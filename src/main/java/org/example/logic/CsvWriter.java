package org.example.logic;
import org.example.logic.exceptions.CsvException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import static org.example.logic.Main.setFirstFlush;

public class CsvWriter {

    private CsvWriter() {}

    /**
     * Writes results into Csv file
     * @param results the list of records
     * @param filename the name of the file to write
     * @throws CsvException if error writing to file
     */
    public static void writeCsvFile(List<Record> results, String filename) throws CsvException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            if (Main.isFirstFlush()) {
                // write first line
                writer.append("dataset;#TrainingRelease;%Training;%Defective in Training;%Defective in Testing;Classifier;"
                        + "Balancing;Feature Selection;Sensitivity;TP;FP;TN;FN;Precision;Recall;AUC;Kappa");
                writer.newLine();
                setFirstFlush(false);
            }

            for (Record res: results) {
                // write data on file
                writer.append(res.getDataset());
                writer.append(";");
                writer.append(String.valueOf(res.getNumTrainingReleases()));
                writer.append(";");
                writer.append(String.valueOf(res.getPercentTraining()));
                writer.append(";");
                writer.append(String.valueOf(res.getPercentDefTraining()));
                writer.append(";");
                writer.append(String.valueOf(res.getPercentDefTesting()));
                writer.append(";");
                writer.append(res.getClassifier());
                writer.append(";");
                writer.append(res.getBalancing());
                writer.append(";");
                writer.append(res.getFeatureSel());
                writer.append(";");
                writer.append(res.getSensitivity());
                writer.append(";");
                writer.append(String.valueOf(res.getTp()));
                writer.append(";");
                writer.append(String.valueOf(res.getFp()));
                writer.append(";");
                writer.append(String.valueOf(res.getTn()));
                writer.append(";");
                writer.append(String.valueOf(res.getFn()));
                writer.append(";");
                writer.append(String.valueOf(res.getPrecision()));
                writer.append(";");
                writer.append(String.valueOf(res.getRecall()));
                writer.append(";");
                writer.append(String.valueOf(res.getAuc()));
                writer.append(";");
                writer.append(String.valueOf(res.getKappa()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new CsvException(e);
        }
    }
}