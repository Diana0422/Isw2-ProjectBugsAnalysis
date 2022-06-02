package org.example.logic.sensitivity;

import org.example.logic.exceptions.ClassificationException;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

public class SensitiveThreshold extends Sensitivity {

    public SensitiveThreshold(FilteredClassifier fc, Instances training) throws ClassificationException {
        super(fc);
        this.name = "Sensitive Threshold";
        applySensitiveThreshold(training);
    }

    private void applySensitiveThreshold(Instances training) throws ClassificationException {
        try {
            cost.setCell(0, 1, 1.0);
            cost.setCell(1, 0, 1.0);
            csc.setCostMatrix(cost);
            csc.setMinimizeExpectedCost(true);
            csc.setClassifier(fc);
            csc.buildClassifier(training);
        } catch (Exception e) {
            throw new ClassificationException(e.getCause());
        }

    }
}
