package com.tyl.commom;

public class Count {

	private int successCount;
	private int failCount;
	private int sumCount;

	public Count() {
		successCount = 0;
		failCount = 0;
		sumCount = 0;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public int getSumCount() {
		return sumCount;
	}

	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}

	public synchronized void addSuccessCount() {
		successCount++;
		sumCount++;
	}

	public synchronized void addFailCount() {
		failCount++;
		sumCount++;
	}
}
