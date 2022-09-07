package org.example.logic.sensitivity;

import org.example.logic.exceptions.ClassificationException;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

public class SensitiveLearning extends Sensitivity {

    public SensitiveLearning(FilteredClassifier fc, Instances training) throws ClassificationException {
        super(fc);
        this.name = "Sensitive Learning";
        applySensitiveLearning(training);
    }

    private void applySensitiveLearning(Instances training) throws ClassificationException {
        try {
            cost.setCell(0, 1, 10.0);
            cost.setCell(1, 0, 1.0);
            csc.setCostMatrix(cost);
            csc.setMinimizeExpectedCost(false);
            csc.setClassifier(fc);
            csc.buildClassifier(training);
        } catch (Exception e) {
            throw new ClassificationException(e.getCause());
        }
    }

}