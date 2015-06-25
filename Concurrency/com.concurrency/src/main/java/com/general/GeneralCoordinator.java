package com.general;


public abstract class GeneralCoordinator {
	private boolean emptyList;
	private boolean endOfFile;
	private boolean processEnd;
	private boolean stopApp;
	private long startTime;

	public  GeneralCoordinator() {
		super();
		emptyList = true;
		endOfFile = false;
		processEnd = false;
		stopApp = false;
	}

	public boolean isProcessEnd() {
		return processEnd;
	}

	public void setProcessEnd(boolean processEnd) {
		this.processEnd = processEnd;
	}

	public boolean isEndOfFile() {
		return endOfFile;
	}

	public void setEndOfFile(boolean endOfFile) {
		this.endOfFile = endOfFile;
	}

	public boolean isEmptyList() {
		return emptyList;
	}

	public void setEmptyList(boolean emptyList) {
		this.emptyList = emptyList;
	}

	public boolean isStopApp() {
		return stopApp;
	}

	public void setStopApp(boolean stopApp) {
		this.stopApp = stopApp;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
}
