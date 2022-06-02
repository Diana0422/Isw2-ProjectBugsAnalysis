package org.example.logic.exceptions;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvException extends Exception {
    public CsvException(IOException e) {
        super(e);
        Logger.getGlobal().log(Level.SEVERE, "Error writing file csv.");
    }
}
