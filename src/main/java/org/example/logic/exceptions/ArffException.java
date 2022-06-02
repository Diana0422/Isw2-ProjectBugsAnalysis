package org.example.logic.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArffException extends Exception {
    public ArffException(FileNotFoundException e) {
        super(e);
        Logger.getGlobal().log(Level.SEVERE, "File not found.");
    }

    public ArffException(IOException e1) {
        super(e1);
        Logger.getGlobal().log(Level.SEVERE, "Error reading/writing arff files.");
    }
}
