package org.example.logic.balancing;

import org.example.logic.exceptions.BalancingException;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.supervised.instance.Resample;

public class Oversampling extends Balancing {

    public Oversampling(Instances trainset, Instances testset, float percent) throws BalancingException {
        super(trainset, testset, percent);
        this.name = "Oversampling";
        setOversampling();
    }

    private void setOversampling() throws BalancingException {
        String[] opts;
        this.filterClassifier = new FilteredClassifier();

        try {
            Resample resample = new Resample();
            opts = new String[]  {"-B", "1.0", "-Z", String.valueOf(2*percent*100)};
            resample.setOptions(opts);
            this.filterClassifier.setFilter(resample);
        } catch (Exception e) {
            throw new BalancingException(e);
        }
    }

}