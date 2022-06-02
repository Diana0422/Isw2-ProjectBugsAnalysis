package org.example.logic.sensitivity;

import org.example.logic.exceptions.ClassificationException;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

public class NoSensitivity extends Sensitivity{

    public NoSensitivity(FilteredClassifier fc, Instances training) throws ClassificationException {
        super(fc);
        this.name = "No Sensitivity";
        applyNoSensitivity(training);
    }

    private void applyNoSensitivity(Instances training) throws ClassificationException {
        try {
            cost.setCell(0, 1, 1.0);
            cost.setCell(1, 0, 1.0);
            csc.setMinimizeExpectedCost(false);
            csc.setClassifier(fc);
            csc.setCostMatrix(cost);
            csc.buildClassifier(training);
        } catch (Exception e) {
            throw new ClassificationException(e);
        }
    }


}
