package org.example.logic.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassificationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ClassificationException(Throwable e) {
        super(e.getMessage(), e);
        Logger.getGlobal().log(Level.SEVERE, "Error building classifier");
    }

}
