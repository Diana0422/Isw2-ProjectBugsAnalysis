package org.example.logic.selection;

import org.example.logic.exceptions.DatasetCreationException;
import org.example.logic.exceptions.SelectionException;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class BestFirst extends FeatureSelection {

    public BestFirst(DataSource training, DataSource testing) throws DatasetCreationException, SelectionException {
        super(training, testing);
        this.name = "Best First";
        setBestFirst();
    }

    private void setBestFirst() throws SelectionException {
        Instances trainingFiltered;
        Instances testingFiltered;

        try {
            AttributeSelection filter = new AttributeSelection();

            CfsSubsetEval eval = new CfsSubsetEval();
            GreedyStepwise search = new GreedyStepwise();
            search.setSearchBackwards(true);

            filter.setEvaluator(eval);
            filter.setSearch(search);
            filter.setInputFormat(training);

            trainingFiltered = Filter.useFilter(training, filter);
            testingFiltered = Filter.useFilter(testing, filter);

            int numAttrFiltered = trainingFiltered.numAttributes();

            trainingFiltered.setClassIndex(numAttrFiltered - 1);
            testingFiltered.setClassIndex(numAttrFiltered - 1);
            this.training = trainingFiltered;
            this.testing = testingFiltered;
        } catch (Exception e) {
            throw new SelectionException(e);
        }
    }

}