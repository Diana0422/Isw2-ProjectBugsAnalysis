package org.example.logic.sensitivity;

import weka.classifiers.CostMatrix;
import weka.classifiers.meta.CostSensitiveClassifier;
import weka.classifiers.meta.FilteredClassifier;

public abstract class Sensitivity {

    protected CostMatrix cost;
    protected CostSensitiveClassifier csc;
    protected FilteredClassifier fc;
    protected String name;

    protected Sensitivity(FilteredClassifier fc) {
        this.cost = new CostMatrix(2);
        this.csc = new CostSensitiveClassifier();
        this.fc = fc;
    }

    public CostMatrix getCost() {
        return cost;
    }

    public void setCost(CostMatrix cost) {
        this.cost = cost;
    }

    public CostSensitiveClassifier getCostSensitiveClassifier() {
        return csc;
    }

    public String getName() {
        return name;
    }
}
