package com.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.general.GeneralCoordinator;
import com.general.Task;

public class Coordinator extends GeneralCoordinator{

	private BlockingQueue<Task> tasksForProcess;
	private BlockingQueue<Task> tasksProcessed;

	public Coordinator() {
		super();
		tasksForProcess = new LinkedBlockingQueue<Task>();
		tasksProcessed = new LinkedBlockingQueue<Task>();
	}

	public Task getTask(BlockingQueue<Task> queue) throws InterruptedException {
		return queue.take();
	}

	public void addTask(BlockingQueue<Task> queue, String element)
			throws InterruptedException {
		queue.put(new Task(element));
	}

	public BlockingQueue<Task> getTasksForProcess() {
		return tasksForProcess;
	}

	public void setTasksForProcess(BlockingQueue<Task> tasksForProcess) {
		this.tasksForProcess = tasksForProcess;
	}

	public BlockingQueue<Task> getTasksProcessed() {
		return tasksProcessed;
	}

	public void setTasksProcessed(BlockingQueue<Task> tasksProcessed) {
		this.tasksProcessed = tasksProcessed;
	}

}
