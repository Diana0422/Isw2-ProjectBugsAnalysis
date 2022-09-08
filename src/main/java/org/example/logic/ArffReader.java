package org.example.logic;
import org.example.logic.exceptions.ArffException;
import org.example.logic.exceptions.DatasetCreationException;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to read the initial dataset in ARFF format
 */
public class ArffReader {

    private static final String ATTRIBUTE_HEADER = "@attribute";
    private static final String RELATION_HEADER = "@relation";
    private static final String DATA_HEADER = "@data";

    private ArffReader() {}

    /**
     * Creates the ARFF file for the training set, implementing WalkForward technique
     * @param filenameIn arff file to read (whole dataset)
     * @param filenameOut arff file to write (the training set)
     * @param maxRelease the index of the release to include in the testing set
     * @return DataSource representing the training set
     */
    public static DataSource createTrainingSet(String filenameIn, String filenameOut, int maxRelease) throws DatasetCreationException, ArffException {
        String line;
        String newLine;
        String [] values;

        // read original dataset and write on new arff file
        try (BufferedReader read = new BufferedReader(new FileReader(filenameIn));
             BufferedWriter write = new BufferedWriter(new FileWriter(filenameOut))) {
            while ((line = read.readLine()) != null) {
                if (line.contains(ATTRIBUTE_HEADER) || line.contains(RELATION_HEADER) || line.contains(DATA_HEADER) || line.equals("")) {
                    write.append(line);
                    write.newLine();
                } else {
                    newLine = line;
                    values = newLine.split(",");

                    int currentRelease = Integer.parseInt(values[0]);

                    /* Copy lines that have release that happen before the testing set release */
                    if (currentRelease <= maxRelease-1) {
                        write.append(line);
                        write.newLine();
                    } else {
                        break;	// testing set release comes later
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new ArffException(e);
        } catch (IOException e1) {
            throw new ArffException(e1);
        }

        // create training set datasource from created arff file
        try {
            return new DataSource(filenameOut);
        } catch (Exception e) {
            throw new DatasetCreationException("Error creating dataset", e);
        }
    }

    /**
     * Creates the ARFF file for the testing set, implementing the WalkForward technique
     * @param filenameIn arff file to read (whole dataset)
     * @param filenameOut arff file to write (the testing set)
     * @param maxRelease the testing set release
     * @return DataSource representing the testing set
     */
    public static DataSource createTestingSet(String filenameIn, String filenameOut, int maxRelease) throws DatasetCreationException, ArffException {
        String line;
        String newLine;
        String [] values;

        // read original dataset and write on new arff file
        try (BufferedReader reader = new BufferedReader(new FileReader(filenameIn));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filenameOut))) {
            while ((line = reader.readLine()) != null) {
                if (line.contains(ATTRIBUTE_HEADER) || line.contains(RELATION_HEADER) || line.contains(DATA_HEADER) || line.equals("")) {
                    writer.append(line);
                    writer.newLine();
                } else {
                    newLine = line;
                    values = line.split(",");
                    int currentRelease = Integer.parseInt(values[0]);

                    /* Copy only the lines with files in release expected (based on WalkForward) */
                    if (currentRelease == maxRelease) {
                        writer.append(newLine);
                        writer.newLine();
                    } else if  (currentRelease > maxRelease) {
                        break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new ArffException(e);
        } catch (IOException e1) {
            throw new ArffException(e1);
        }

        // create testing set datasource from created arff file
        try {
            return new DataSource(filenameOut);
        } catch (Exception e) {
            throw new DatasetCreationException("Error creating dataset", e);
        }
    }

    /**
     * Collects information about the input dataset
     * @param filenameIn name of file in input
     * @param releases array that collects number of files for each release
     * @param buggy array that collects number of buggy for each release
     * @throws ArffException if error reading file
     */
    public static void getInfo(String filenameIn, int [] releases, int [] buggy) throws ArffException {
        String line;
        String [] values;

        // read original dataset and write infos on files
        try (BufferedReader reader = new BufferedReader(new FileReader(filenameIn))) {
            while ((line = reader.readLine()) != null) {
                if (!line.contains(ATTRIBUTE_HEADER) && !line.contains(RELATION_HEADER) && !line.contains(DATA_HEADER) && !line.equals("")) {
                    values = line.split(",");

                    int num = Integer.parseInt(values[0])-1;
                    releases[num]++; // add the release to array

                    if (values[values.length-1].compareTo("Yes") == 0) { // add a buggy element to array
                        buggy[num]++;
                    }
                }
            }
        } catch (IOException e) {
            throw new ArffException(e);
        }
    }

}