package org.example.logic.balancing;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

public abstract class Balancing {

    private Instances training;
    private Instances testing;
    protected FilteredClassifier filterClassifier;
    protected float percent;
    protected String name;

    protected Balancing(Instances trainset, Instances testset, float percent) {
        this.training = trainset;
        this.testing = testset;
        this.percent = percent;
    }

    public Instances getTraining() {
        return training;
    }

    public void setTraining(Instances training) {
        this.training = training;
    }

    public Instances getTesting() {
        return testing;
    }

    public void setTesting(Instances testing) {
        this.testing = testing;
    }

    public FilteredClassifier getFilteredClassifier() {
        return this.filterClassifier;
    }

    public String getName() {
        return name;
    }

}