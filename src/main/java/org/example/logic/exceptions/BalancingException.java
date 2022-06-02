package org.example.logic.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BalancingException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BalancingException(Throwable e) {
        super(e.getMessage(), e);
        Logger.getGlobal().log(Level.SEVERE, "Error balancing dataset");
    }

}
