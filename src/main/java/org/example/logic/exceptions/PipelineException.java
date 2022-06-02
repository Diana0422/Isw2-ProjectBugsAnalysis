package org.example.logic.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PipelineException extends Exception {

    public PipelineException(Exception e) {
        super(e);
        Logger.getGlobal().log(Level.SEVERE, e.getMessage());
    }
}
