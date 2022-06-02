package org.example.logic.balancing;

import org.example.logic.exceptions.BalancingException;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.supervised.instance.SpreadSubsample;

public class Undersampling extends Balancing {

    public Undersampling(Instances trainset, Instances testset, float percent) throws BalancingException {
        super(trainset, testset, percent);
        this.name = "Undersampling";
        setUndersampling();
    }

    private void setUndersampling() throws BalancingException {
        String[] opts;
        this.filterClassifier = new FilteredClassifier();

        try {
            SpreadSubsample subsample = new SpreadSubsample();
            opts = new String[]  {"-M", "1.0"};
            subsample.setOptions(opts);
            this.filterClassifier.setFilter(subsample);
        } catch (Exception e) {
            throw new BalancingException(e);
        }
    }


}