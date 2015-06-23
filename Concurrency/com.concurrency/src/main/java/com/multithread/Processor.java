package com.multithread;

public class Processor implements Runnable {

	private Coordinator inputCoordinator;
	private Coordinator outputCoordinator;

	public Processor(Coordinator engine, Coordinator outputEngine) {
		super();
		this.inputCoordinator = engine;
		this.outputCoordinator = outputEngine;
	}

	public void process() throws InterruptedException {
		Task task = null;
		synchronized (inputCoordinator) {
			if (!inputCoordinator.tasks.isEmpty()) {
				task = inputCoordinator.getTask();
				addProcessedElement(processElement(task.getElement()));
				System.out.println("size: "+inputCoordinator.tasks.size());
			}
		}
	}

	public String processElement(String element) {
		return element.toUpperCase();
	}

	public void addProcessedElement(String element) {
		synchronized (outputCoordinator) {
			outputCoordinator.addTask(element);
			outputCoordinator.emptyList = false;
			outputCoordinator.notify();
		}
	}

	@Override
	public void run() {
		while (!outputCoordinator.isProcessend()) {
			System.out.println("process: "+Thread.currentThread().getName());
			try {
				process();
				if(inputCoordinator.isEndOfFile() & inputCoordinator.tasks.isEmpty()){
					outputCoordinator.setProcessEnd(true);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
