package org.example.logic.exceptions;

public class DatasetCreationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DatasetCreationException(String message, Throwable e) {
        super(message, e);
    }

}