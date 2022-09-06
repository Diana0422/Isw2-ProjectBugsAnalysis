package org.example.logic;

import org.example.logic.exceptions.DatasetCreationException;
import org.example.logic.exceptions.PipelineException;
import org.example.logic.exceptions.SelectionException;
import org.example.logic.selection.BestFirst;
import org.example.logic.selection.FeatureSelection;
import org.example.logic.selection.NoSelection;
import weka.core.converters.ConverterUtils.DataSource;

public class Analyzer {

    private final Pipeline pipe;
    private float percent;
    private final DataSource trainSource;
    private final DataSource testSource;
    private String dataset;
    private final int numReleases;
    private final float percTraining;
    private final float percDefTraining;
    private final float percDefTesting;

    public Analyzer(DataSource trainSource, DataSource testSource, Pipeline pipe, float percent, Record result) {
        this.percent = percent;
        this.trainSource = trainSource;
        this.testSource = testSource;
        this.pipe = pipe;
        this.dataset = result.getDataset();
        this.numReleases = result.getNumTrainingReleases();
        this.percDefTesting = result.getPercentDefTesting();
        this.percDefTraining = result.getPercentDefTraining();
        this.percTraining = result.getPercentTraining();
    }

    public void pipelinePhases() throws PipelineException {
        // attribute selection
        FeatureSelection fs;

        // new result record
        Record result = new Record(dataset, numReleases, percTraining, percDefTraining, percDefTesting);

        try {
            // no selection
            fs = new NoSelection(trainSource, testSource);
            pipe.setFeatureSel(fs, result);
            pipe.pipelineBalancing(percent, result);

            // best first
            fs = new BestFirst(trainSource, testSource);
            pipe.setFeatureSel(fs, result);
            pipe.pipelineBalancing(percent, result);
        } catch (DatasetCreationException | SelectionException e) {
            e.printStackTrace();
        }
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

}