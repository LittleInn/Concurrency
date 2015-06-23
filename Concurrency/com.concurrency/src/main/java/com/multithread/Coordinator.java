package com.multithread;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Coordinator {

	public Coordinator() {
		super();
		tasks = new LinkedList<Task>();
		emptyList = true;
		endOfFile = false;
		processEnd = false;
	}

	List<Task> tasks;
	boolean emptyList;
	boolean endOfFile ;
	boolean processEnd;

	public boolean isProcessend() {
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

	public Task getTask() {
		Iterator<Task> iterator = tasks.iterator();
		Task element = null;
		if (iterator.hasNext()) {
			element = iterator.next();
		}
		tasks.remove(element);
		return element;
	}

	public void addTask(String element) {
		tasks.add(new Task(element));
		emptyList = false;
	}

}
