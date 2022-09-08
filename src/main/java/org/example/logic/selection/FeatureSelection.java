package org.example.logic.selection;

import org.example.logic.exceptions.DatasetCreationException;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * Class to pipeline Feature Selection
 */
public abstract class FeatureSelection {

    protected Instances training;
    protected Instances testing;
    protected String name;

    protected FeatureSelection(DataSource trainSource, DataSource testSource) throws DatasetCreationException {
        try {
            this.training = trainSource.getDataSet();
            this.testing = testSource.getDataSet();
        } catch (Exception e) {
            throw new DatasetCreationException("Error creating dataset", e.getCause());
        }
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

    public String getName() {
        return name;
    }



}