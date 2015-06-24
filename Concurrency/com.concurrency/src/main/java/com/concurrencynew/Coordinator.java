package com.concurrencynew;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.multithread.Task;

public class Coordinator {

	public Coordinator() {
		super();
		tasks = new LinkedBlockingQueue<Task>();
		endOfFile = false;
		processEnd = false;
	}

	private BlockingQueue<Task> tasks;
	private boolean endOfFile ;
	private boolean processEnd;

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

	public Task getTask() throws InterruptedException {
		Task element = tasks.take();
		return element;
	}

	public void addTask(String element) throws InterruptedException {
		tasks.put(new Task(element));
	}

	public BlockingQueue<Task> getTasks() {
		return tasks;
	}

	public void setTasks(BlockingQueue<Task> tasks) {
		this.tasks = tasks;
	}

}
