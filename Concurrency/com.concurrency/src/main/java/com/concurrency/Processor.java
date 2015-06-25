package com.concurrency;

import com.general.Task;

public class Processor implements Runnable{

	private Coordinator coordinator;

	public Processor(Coordinator coordinator) {
		super();
		this.coordinator = coordinator;
	}

	public void process() throws InterruptedException {
		Task task = null;
			if (!coordinator.getTasksForProcess().isEmpty()) {
				task = coordinator.getTask(coordinator.getTasksForProcess());
				addProcessedElement(processElement(task.getElement()));
			}
	}

	public String processElement(String element) {
		return new StringBuilder(element).reverse().toString();
	}

	public void addProcessedElement(String element) throws InterruptedException {
			coordinator.addTask(coordinator.getTasksProcessed(), element);
	}

	@Override
	public void run() {
		while (!coordinator.isProcessEnd()) {
			try {
				process();
				if(coordinator.isEndOfFile() & coordinator.getTasksForProcess().isEmpty()){
					coordinator.setProcessEnd(true);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
