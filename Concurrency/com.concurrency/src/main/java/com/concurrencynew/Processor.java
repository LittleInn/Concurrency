package com.concurrencynew;

import com.multithread.Task;

public class Processor implements Runnable{

	private Coordinator inputCoordinator;
	private Coordinator outputCoordinator;

	public Processor(Coordinator engine, Coordinator outputEngine) {
		super();
		this.inputCoordinator = engine;
		this.outputCoordinator = outputEngine;
	}

	public void process() throws InterruptedException {
		Task task = null;
			if (!inputCoordinator.getTasks().isEmpty()) {
				task = inputCoordinator.getTask();
				addProcessedElement(processElement(task.getElement()));
			}
	}

	public String processElement(String element) {
		return element.toUpperCase();
	}

	public void addProcessedElement(String element) throws InterruptedException {
			outputCoordinator.addTask(element);
	}

	@Override
	public void run() {
		while (!outputCoordinator.isProcessEnd()) {
//			System.out.println("process: "+Thread.currentThread().getName());
			try {
				process();
				if(inputCoordinator.isEndOfFile() & inputCoordinator.getTasks().isEmpty()){
					outputCoordinator.setProcessEnd(true);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
