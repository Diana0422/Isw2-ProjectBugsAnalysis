package org.example.logic;

public class Record {
    private String dataset; // name of the dataset
    private final int numTrainingReleases; // number of training releases
    private final float percentTraining; // percentage data in training (training data/total)
    private final float percentDefTraining; // percentage of defective data in training
    private final float percentDefTesting; // percentage of defective data in testing
    private String classifier; // name of the classifier
    private String balancing; // name of the balancing technique
    private String featureSel; // name of the feature selection technique
    private String sensitivity;
    private double tp; // true positives
    private double fp; // false positives
    private double tn; // true negatives
    private double fn; // false negatives
    private double precision; // precision metric
    private double recall; // recall metric
    private double auc; // auc metric
    private double kappa; // kappa metric

    public Record(String datasetName, int numReleases, float percTraining, float percDefTraining, float percDefTesting) {
        this.dataset = datasetName;
        this.numTrainingReleases = numReleases;
        this.percentTraining = percTraining;
        this.percentDefTesting = percDefTesting;
        this.percentDefTraining = percDefTraining;
        this.classifier = "";
        this.balancing = "";
        this.featureSel = "";
        this.sensitivity = "";
        this.tp = 0;
        this.fp = 0;
        this.tn = 0;
        this.fn = 0;
        this.precision = 0;
        this.recall = 0;
        this.auc = 0;
        this.kappa = 0;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public int getNumTrainingReleases() {
        return numTrainingReleases;
    }

    public float getPercentTraining() {
        return percentTraining;
    }

    public float getPercentDefTraining() {
        return percentDefTraining;
    }

    public float getPercentDefTesting() {
        return percentDefTesting;
    }

    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getBalancing() {
        return balancing;
    }

    public void setBalancing(String balancing) {
        this.balancing = balancing;
    }

    public String getFeatureSel() {
        return featureSel;
    }

    public void setFeatureSel(String featureSel) {
        this.featureSel = featureSel;
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public double getTp() {
        return tp;
    }

    public void setTp(double tp) {
        this.tp = tp;
    }

    public double getFp() {
        return fp;
    }

    public void setFp(double fp) {
        this.fp = fp;
    }

    public double getTn() {
        return tn;
    }

    public void setTn(double tn) {
        this.tn = tn;
    }

    public double getFn() {
        return fn;
    }

    public void setFn(double d) {
        this.fn = d;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double d) {
        this.precision = d;
    }

    public double getRecall() {
        return recall;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public double getAuc() {
        return auc;
    }

    public void setAuc(double d) {
        this.auc = d;
    }

    public double getKappa() {
        return kappa;
    }

    public void setKappa(double kappa) {
        this.kappa = kappa;
    }


}