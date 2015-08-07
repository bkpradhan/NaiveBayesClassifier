package me.bkpradhan.edu.njit.u2015.cs634.domain;

import java.util.Arrays;

import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

public class PredictionResult {
	private long 	cid;
	private String 	name;
	private String  record;
	private String 	prediction;
	private String 	match;
	private double[] probDistribution;
	private String errorMessage;
	private String statistics;
	
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getPrediction() {
		return prediction;
	}
	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public double[] getProbDistribution() {
		return probDistribution;
	}
	public void setProbDistribution(double[] probDistribution) {
		this.probDistribution = probDistribution;
	}

	public String getStatistics() {
		return statistics;
	}
	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
	@Override
	public String toString() {
		return "PredictionResult [cid=" + cid + ", name=" + name + ", record="
				+ record + ", prediction=" + prediction + ", match=" + match
				+ ", probDistribution=" + Arrays.toString(probDistribution)
				+ ", errorMessage=" + errorMessage + "]";
	}
	
	
	
}
