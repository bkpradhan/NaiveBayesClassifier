package me.bkpradhan.edu.njit.u2015.cs634.domain;

import java.util.List;

public class PredictionSummary {
	private List<PredictionResult> results;
	public List<PredictionResult> getResults() {
		return results;
	}
	public void setResults(List<PredictionResult> results) {
		this.results = results;
	}
	public PredictionStatistics getStat() {
		return stat;
	}
	public void setStat(PredictionStatistics stat) {
		this.stat = stat;
	}
	private PredictionStatistics stat;
	@Override
	public String toString() {
		return "PredictionSummary ["+ stat.getSummary() + "]";
	}

}
