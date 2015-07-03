package com.multithread;

import java.util.Date;

import com.general.Task;

public class Processor implements Runnable {

	private Coordinator inputCoordinator;
	private Coordinator outputCoordinator;
	private static long processedLines = 0;

	public Processor(Coordinator inputCoordinator, Coordinator outputCoordinator) {
		super();
		this.inputCoordinator = inputCoordinator;
		this.outputCoordinator = outputCoordinator;
		outputCoordinator.setStartTime(new Date().getTime());
	}

	@Override
	public void run() {
		while (!outputCoordinator.isProcessEnd()) {
			process();
		}
	}

	private void process() {
		try {
			processElement();
			if (inputCoordinator.isEndOfFile()
					& inputCoordinator.getTasks().isEmpty()) {
				outputCoordinator.setProcessEnd(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void processElement() throws InterruptedException {
		Task task = null;
		if (!inputCoordinator.getTasks().isEmpty()) {
			task = inputCoordinator.getTask();
			addProcessedElement(reverseElement(task.getElement()));
		}
	}

	private String reverseElement(String element) {
		return new StringBuilder(element).reverse().toString();
	}

	private void addProcessedElement(String element) {
		outputCoordinator.addTask(element);
		outputCoordinator.setEmptyList(false);
		outputCoordinator.setProcessedLinesCount(processedLines++);
	}

}
