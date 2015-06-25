package com.multithread;

import java.util.Date;

import com.general.Task;

public class Processor implements Runnable {

	private Coordinator inputCoordinator;
	private Coordinator outputCoordinator;

	public Processor(Coordinator inputCoordinator, Coordinator outputCoordinator) {
		super();
		this.inputCoordinator = inputCoordinator;
		this.outputCoordinator = outputCoordinator;
		outputCoordinator.setStartTime(new Date().getTime());
	}

	public void process() throws InterruptedException {
		Task task = null;
		synchronized (inputCoordinator) {
			if (!inputCoordinator.getTasks().isEmpty()) {
				task = inputCoordinator.getTask();
				addProcessedElement(processElement(task.getElement()));
			}
		}
	}

	public String processElement(String element) {
		return new StringBuilder(element).reverse().toString();
	}

	public void addProcessedElement(String element) {
		synchronized (outputCoordinator) {
			outputCoordinator.addTask(element);
			outputCoordinator.setEmptyList(false);
			// outputCoordinator.emptyList = false;
			outputCoordinator.notify();
		}
	}

	@Override
	public void run() {
		while (!outputCoordinator.isProcessEnd()) {
			try {
				process();
				if (inputCoordinator.isEndOfFile()
						& inputCoordinator.getTasks().isEmpty()) {
					outputCoordinator.setProcessEnd(true);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
