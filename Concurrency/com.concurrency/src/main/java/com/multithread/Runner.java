package com.multithread;

import com.general.AppManagment;

public class Runner {
	
	private static void runApp() {

		Coordinator inputCoordinator = new Coordinator();
		Coordinator outputCoordinator = new Coordinator();

		Thread threadMonitor = new Monitor(inputCoordinator, outputCoordinator);
		threadMonitor.start();

		Thread producer = new Thread(new Producer(inputCoordinator,
				AppManagment.getInputFile()));
		producer.start();

		for (int i = 0; i < AppManagment.getThreadCount(); i++) {
			new Thread(new Processor(inputCoordinator, outputCoordinator))
					.start();
		}

		Thread consumer = new Thread(new Consumer(outputCoordinator,
				AppManagment.getOutputFile()));
		consumer.start();
	}

	public static void main(String[] args) {
		Runner.runApp();
	}

}
