package org.example.logic;
import org.example.logic.balancing.*;
import org.example.logic.classification.Classification;
import org.example.logic.exceptions.*;
import org.example.logic.selection.FeatureSelection;
import org.example.logic.sensitivity.NoSensitivity;
import org.example.logic.sensitivity.SensitiveLearning;
import org.example.logic.sensitivity.SensitiveThreshold;
import org.example.logic.sensitivity.Sensitivity;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.CostSensitiveClassifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

import java.util.List;

public class Pipeline {

    private Balancing balancing;
    private Sensitivity sensitivity;
    private FeatureSelection featureSel;
    private Classification classification;
    private final List<Record> results;

    public Pipeline(List<Record> results) {
        this.results = results;
    }

    public void pipelineBalancing(float percent, Record result) throws PipelineException {
        // balancing
        Balancing balance;

        try {
            // no balancing
            balance = new NoBalancing(featureSel.getTraining(), featureSel.getTesting(), percent);
            this.setBalancing(balance, result);
            pipelineClassification(result);

            // undersampling
            balance = new Undersampling(featureSel.getTraining(), featureSel.getTesting(), percent);
            this.setBalancing(balance, result);
            pipelineClassification(result);

            // oversampling
            balance = new Oversampling(featureSel.getTraining(), featureSel.getTesting(), percent);
            this.setBalancing(balance, result);
            pipelineClassification(result);

            // SMOTE
            balance = new SMOTEBalancing(featureSel.getTraining(), featureSel.getTesting(), percent);
            this.setBalancing(balance, result);
            pipelineClassification(result);
        } catch (BalancingException e) {
            throw new PipelineException(e);
        }

    }

    private void pipelineSensitivity(Classification c, Record result) throws PipelineException {
        // sensitivity
        Instances training = c.getTraining();
        FilteredClassifier filteredClassifier = c.getFilteredClassifier();
        Sensitivity sensit;

        try {
            // no sensitivity
            sensit = new NoSensitivity(filteredClassifier, training);
            this.setSensitivity(sensit, result);
            evaluation(result);

            // sensitive threshold
            sensit = new SensitiveThreshold(filteredClassifier, training);
            this.setSensitivity(sensit, result);
            evaluation(result);

            // sensitive learning
            sensit = new SensitiveLearning(filteredClassifier, training);
            this.setSensitivity(sensit, result);
            evaluation(result);

        } catch (ClassificationException | EvaluationException e) {
            throw new PipelineException(e);
        }
    }

    public void pipelineClassification(Record result) throws PipelineException {

        try {
            // classification
            Classification c = new Classification(featureSel.getTraining(), featureSel.getTesting(), balancing.getFilteredClassifier());
            this.classification = c;

            // ibk
            c.setIbkClassifier(result);
            pipelineSensitivity(c, result);
            evaluation(result);

            // naive bayes
            c.setNaiveBayesClassifier(result);
            pipelineSensitivity(c, result);
            evaluation(result);


            // random forest
            c.setRandomForestClassifier(result);
            pipelineSensitivity(c, result);
            evaluation(result);

        } catch (ClassificationException e) {
            throw new PipelineException(e);
        } catch (EvaluationException e) {
            e.printStackTrace();
        }
    }

    private void evaluation(Record result) throws EvaluationException {
        CostSensitiveClassifier csc = sensitivity.getCostSensitiveClassifier();
        Instances testing = classification.getTesting();
        Evaluation eval;
        Record newResult = new Record(result.getDataset(), result.getNumTrainingReleases(), result.getPercentTraining(), result.getPercentDefTraining(), result.getPercentDefTesting());
        newResult.setClassifier(result.getClassifier());
        newResult.setBalancing(result.getBalancing());
        newResult.setFeatureSel(result.getFeatureSel());
        newResult.setSensitivity(result.getSensitivity());

        try {
            eval = new Evaluation(testing, csc.getCostMatrix());
            eval.evaluateModel(csc, testing);

            newResult.setFn(eval.numFalseNegatives(1));
            newResult.setFp(eval.numFalsePositives(1));
            newResult.setTp(eval.numTruePositives(1));
            newResult.setTn(eval.numTrueNegatives(1));
            newResult.setPrecision(eval.precision(1));
            newResult.setRecall(eval.recall(1));
            newResult.setAuc(eval.areaUnderROC(1));
            newResult.setKappa(eval.kappa());
            results.add(newResult);
        } catch (Exception e) {
            throw new EvaluationException(e.getCause());
        }
    }

    public Balancing getBalancing() {
        return balancing;
    }
    public void setBalancing(Balancing balancing, Record result) {
        this.balancing = balancing;
        result.setBalancing(balancing.getName());
    }
    public Sensitivity getSensitivity() {
        return sensitivity;
    }
    public void setSensitivity(Sensitivity sensitivity, Record result) {
        this.sensitivity = sensitivity;
        result.setSensitivity(sensitivity.getName());
    }
    public FeatureSelection getFeatureSel() {
        return featureSel;
    }
    public void setFeatureSel(FeatureSelection featureSel, Record result) {
        this.featureSel = featureSel;
        result.setFeatureSel(featureSel.getName());
    }

    public List<Record> getResults() {
        return results;
    }
}