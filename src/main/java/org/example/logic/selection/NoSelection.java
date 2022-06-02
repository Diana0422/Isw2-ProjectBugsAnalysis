package org.example.logic.selection;

import org.example.logic.exceptions.DatasetCreationException;
import weka.core.converters.ConverterUtils.DataSource;

public class NoSelection extends FeatureSelection {

    public NoSelection(DataSource training, DataSource testing) throws DatasetCreationException {
        super(training, testing);
        this.name = "No Selection";
        setNoSelection();
    }

    private void setNoSelection() {
        int numAttrNoFilter = training.numAttributes();
        training.setClassIndex(numAttrNoFilter - 1);
        testing.setClassIndex(numAttrNoFilter - 1);
    }

}
