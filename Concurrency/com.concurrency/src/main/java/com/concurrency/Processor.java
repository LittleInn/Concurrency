package com.concurrency;

import com.general.Task;

public class Processor implements Runnable {

	private Coordinator coordinator;
	private static long processedLines = 0;

	public Processor(Coordinator coordinator) {
		super();
		this.coordinator = coordinator;
	}

	@Override
	public void run() {
		while (!coordinator.isProcessEnd()) {
			process();
		}
	}

	private void process() {
		try {
			processElement();
			if (coordinator.isEndOfFile()
					& coordinator.getTasksForProcess().isEmpty()) {
				coordinator.setProcessEnd(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void processElement() throws InterruptedException {
		Task task = null;
		if (!coordinator.getTasksForProcess().isEmpty()) {
			task = coordinator.getTask(coordinator.getTasksForProcess());
			addProcessedElement(reverseElement(task.getElement()));
		}
	}

	private String reverseElement(String element) {
		return new StringBuilder(element).reverse().toString();
	}

	private void addProcessedElement(String element)
			throws InterruptedException {
		coordinator.addTask(coordinator.getTasksProcessed(), element);
		coordinator.setProcessedLinesCount(processedLines++);
	}

}
