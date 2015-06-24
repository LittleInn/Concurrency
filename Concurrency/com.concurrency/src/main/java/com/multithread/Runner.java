package com.multithread;

public class Runner {
	private final static String OUTPUT_FILE_NAME = "D:\\Test_Files\\test1.txt";
	private final static String INPUT_FILE_NAME = "D:\\Test_Files\\test.txt";
	private final static int THREAD_COUNT = 1;

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
		
		Thread threadMonitor = new Monitor(inputCoordinator);
		System.out.println("Is daemon: "+threadMonitor.isDaemon());
		System.out.println(threadMonitor.isAlive());
//threadMonitor.start();
System.out.println("alive: "+threadMonitor.isAlive());
	}

}
