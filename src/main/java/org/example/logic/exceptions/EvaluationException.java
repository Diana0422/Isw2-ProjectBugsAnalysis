package org.example.logic.exceptions;

public class EvaluationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EvaluationException(Throwable e) {
        super("Evaluation Error", e);
    }
}