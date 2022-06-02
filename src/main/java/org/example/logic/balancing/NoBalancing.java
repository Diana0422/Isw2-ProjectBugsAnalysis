package org.example.logic.balancing;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

public class NoBalancing extends Balancing {

    public NoBalancing(Instances training, Instances testing, float percent) {
        super(training, testing, percent);
        this.name = "No Balancing";
        setNoBalancing();
    }

    private void setNoBalancing() {
        this.filterClassifier = new FilteredClassifier();
    }

}