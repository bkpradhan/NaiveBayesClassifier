package me.bkpradhan.edu.njit.u2015.cs634.domain;

public class PredictionStatistics {
	private long totalInstances;
	private long totalMatchedOk;
	private double totalTimeTaken;
	private String name;
	private String summary;
	public String getSummary() {
		return this.toString();
	}
	public long getTotalInstances() {
		return totalInstances;
	}
	public void setTotalInstances(long totalInstances) {
		this.totalInstances = totalInstances;
	}
	public long getTotalMatchedOk() {
		return totalMatchedOk;
	}
	public void setTotalMatchedOk(long totalMatchedOk) {
		this.totalMatchedOk = totalMatchedOk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTotalTimeTaken() {
		return totalTimeTaken;
	}
	public void setTotalTimeTaken(double totalTimeTaken) {
		this.totalTimeTaken = totalTimeTaken;
	}
	@Override
	public String toString() {
		return name+" [ PredictTime= " + totalTimeTaken +", totalInstances= " + totalInstances
				+ ", totalMatchedOk= " + totalMatchedOk + ", totalNotMatched= " + (totalInstances - totalMatchedOk) +", percentMacthedOk= " + (100 * totalMatchedOk)/totalInstances +"% ]";
	}
	
	
}
