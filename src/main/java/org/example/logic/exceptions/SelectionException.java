package org.example.logic.exceptions;

public class SelectionException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SelectionException(Throwable e) {
        super(e.getMessage(), e);
    }

}