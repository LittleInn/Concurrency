package com.multithread;

public class Runner {
	private final static String OUTPUT_FILE_NAME = "D:\\Test_Files\\result.txt";
	private final static String INPUT_FILE_NAME = "D:\\Test_Files\\555M.txt";
	private final static int THREAD_COUNT = 10;

	public static void main(String[] args) {

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

	}

}
