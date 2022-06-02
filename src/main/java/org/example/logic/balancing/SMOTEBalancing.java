package org.example.logic.balancing;

import org.example.logic.exceptions.BalancingException;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.supervised.instance.SMOTE;

public class SMOTEBalancing extends Balancing {

    public SMOTEBalancing(Instances trainset, Instances testset, float percent) throws BalancingException {
        super(trainset, testset, percent);
        this.name = "SMOTE";
        setSMOTE();
    }

    private void setSMOTE() throws BalancingException {
        this.filterClassifier = new FilteredClassifier();
        SMOTE smote = new SMOTE();

        try {
            smote.setInputFormat(this.getTraining());
            this.filterClassifier.setFilter(smote);
        } catch (Exception e) {
            throw new BalancingException(e);
        }
    }

}