package me.bkpradhan.edu.njit.u2015.cs634.domain;

import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;

public class EvaluationResult {
	private long cid;
	private String name;
	private Evaluation eval;
	private Instances train;
	private int foldCount;
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
	public Evaluation getEval() {
		return eval;
	}
	public void setEval(Evaluation eval) {
		this.eval = eval;
	}
	public Instances getTrain() {
		return train;
	}
	public void setTrain(Instances train) {
		this.train = train;
	}
	public int getFoldCount() {
		return foldCount;
	}
	public void setFoldCount(int foldCount) {
		this.foldCount = foldCount;
	}
	public String getStatistics() {
		return statistics;
	}
	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
	@Override
	public String toString() {
		return "\nEvaluationResult= " + name + "\ncid=" + cid + ",\n train=" + train.toSummaryString() + ",\n foldCount=" + foldCount
				+ ", statistics=" + statistics;
	}
	
	
	
}
