package org.example.logic.classification;

import org.example.logic.exceptions.ClassificationException;
import org.example.logic.Record;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

/**
 * Class to pipeline classifier
 */
public class Classification {

    private Instances training;
    private Instances testing;
    private FilteredClassifier fc;
    private String name;

    public Classification(Instances trainset, Instances testset, FilteredClassifier fc) {
        this.training = trainset;
        this.testing = testset;
        this.fc = fc;
    }

    public void setIbkClassifier(Record result) throws ClassificationException {
        try {
            // create and build new classifier
            IBk ibk = new IBk();
            fc.setClassifier(ibk);
            fc.buildClassifier(training);
            this.name = "IBk";
            result.setClassifier(name);
        } catch (Exception e) {
            throw new ClassificationException(e);
        }
    }

    public void setNaiveBayesClassifier(Record result) throws ClassificationException {
        try {
            // create and build new classifier
            NaiveBayes nb = new NaiveBayes();
            fc.setClassifier(nb);
            fc.buildClassifier(training);
            this.name = "Naive Bayes";
            result.setClassifier(name);
        } catch (Exception e) {
            throw new ClassificationException(e);
        }
    }

    public void setRandomForestClassifier(Record result) throws ClassificationException {
        try {
            // create and build new classifier
            RandomForest rf = new RandomForest();
            fc.setClassifier(rf);
            fc.buildClassifier(training);
            this.name = "Random Forest";
            result.setClassifier(name);
        } catch (Exception e) {
            throw new ClassificationException(e);
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

    public FilteredClassifier getFilteredClassifier() {
        return fc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}