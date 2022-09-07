package org.example.logic;

import org.example.logic.exceptions.ArffException;
import org.example.logic.exceptions.CsvException;
import org.example.logic.exceptions.DatasetCreationException;
import org.example.logic.exceptions.PipelineException;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static String projName = "";
    private static boolean firstFlush = true;

    public static boolean isFirstFlush() {return firstFlush;}

    public static void setFirstFlush(boolean val) {firstFlush=val;}

    public static void simpleDebug(String msg) {
        Logger.getGlobal().log(Level.FINE, msg);
    }

    public static void main(String[] args) throws CsvException {
        // read project config
        Properties propB = new Properties();
        Properties propS = new Properties();
        String propBookkeeper = "bookkeeper.properties";
        String propSyncope = "syncope.properties";
        int lastVersion = 0;
        int firstVersion = 0;

        try (InputStream inputStreamB = Main.class.getClassLoader().getResourceAsStream(propBookkeeper);
             InputStream inputStreamS = Main.class.getClassLoader().getResourceAsStream(propSyncope)) {
            propB.load(inputStreamB);
            propS.load(inputStreamS);

            // get activation properties
            if (Boolean.TRUE.equals(Boolean.valueOf(propB.getProperty("project_active")))) {
                Logger.getGlobal().log(Level.WARNING, "Project is Bookkeeper");
                projName = propB.getProperty("project_name");
                lastVersion = Integer.parseInt(propB.getProperty("last_version"));
                firstVersion = Integer.parseInt(propB.getProperty("first_version"));
                firstFlush = Boolean.parseBoolean(propB.getProperty("first_flush"));
            } else {
                Logger.getGlobal().log(Level.WARNING, "Project is Syncope");
                projName = propS.getProperty("project_name");
                lastVersion = Integer.parseInt(propS.getProperty("last_version"));
                firstVersion = Integer.parseInt(propS.getProperty("first_version"));
                firstFlush = Boolean.parseBoolean(propB.getProperty("first_flush"));
            }

        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }

        String datasetIn = projName+"dataset.arff";
        String trainingOut = projName+"train.arff";
        String testingOut = projName+"test.arff";

        Analyzer analyzer = null;
        int current;
        int trainData = 0;
        int trainBuggy = 0;
        double percent;

        // Distributions of buggy files and files distribution in releases
        int [] buggy = new int[95];
        int [] releases = new int[95];

        List<Record> results =new ArrayList<>();
        try {
            ArffReader.getInfo(datasetIn, releases, buggy);
            for (current=firstVersion+1; current<=lastVersion; current++) {
                results = new ArrayList<>();
                /* WALKFORWARD ITERATION */
                /* Create different testing sets and training sets implementing WalkForward
                 * current: represents the boundary release for the creation of training and testing set
                 * */
                simpleDebug("Iteration n°"+ (current - 1));
                DataSource training = ArffReader.createTrainingSet(datasetIn, trainingOut, current);
                DataSource testing = ArffReader.createTestingSet(datasetIn, testingOut, current);

                trainData += releases[current-2];
                trainBuggy += buggy[current-2];

                // get the percentage of buggy files in the current release
                percent = (double)(trainBuggy + buggy[current-2])/(trainData + releases[current-2]);

                // set percent training on total data training + testing
                float percTraining = (float) trainData/(trainData + releases[current-1]);

                // set defective in training data
                float percDefTraining = (float) trainBuggy/(trainData);

                // set defective in testing data
                float percDefTesting = (float) buggy[current-1]/releases[current-1];

                // create new result record (record of the final output)
                Record result = new Record(projName, current-1, percTraining, percDefTraining, percDefTesting);

                analyzer = new Analyzer(training, testing, new Pipeline(results), (float) percent, result);
                analyzer.pipelinePhases();
                CsvWriter.writeCsvFile(results, projName+".csv");
            }
        } catch (DatasetCreationException | ArffException | PipelineException e) {
            Logger.getGlobal().log(Level.SEVERE, e.getMessage());
        }
        CsvWriter.writeCsvFile(results, projName+".csv");
    }
}