package com.multithread;

import java.util.Date;

import com.general.Monitor;

public class Runner {
	private final static String INPUT_FILE_NAME = "D:\\Test_Files\\finall.txt";
	private final static String OUTPUT_FILE_NAME = "D:\\Test_Files\\test.txt";
	private final static int THREAD_COUNT = 10;

	public static void main(String[] args) {
		System.out.println("START: " + new Date().getTime());
		Coordinator inputCoordinator = new Coordinator();
		Coordinator outputCoordinator = new Coordinator();

		Thread producer = new Thread(new Producer(inputCoordinator,
				INPUT_FILE_NAME));
		producer.start();

		for (int i = 0; i < THREAD_COUNT; i++) {
			new Thread(new Processor(inputCoordinator, outputCoordinator))
					.start();
		}

		Thread consumer = new Thread(new Consumer(outputCoordinator,
				OUTPUT_FILE_NAME));
		consumer.start();

		Thread threadMonitor = new Monitor(outputCoordinator);
		threadMonitor.start();
	}

}
