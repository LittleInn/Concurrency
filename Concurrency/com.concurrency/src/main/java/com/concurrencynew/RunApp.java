package com.concurrencynew;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RunApp {
	private static final int THREAD_COUNT = 10;
	private final static String INPUT_FILE_NAME = "D:\\Test_Files\\test.txt";
	private final static String OUTPUT_FILE_NAME = "D:\\Test_Files\\test1.txt";

	public static void main(String[] args) {
		Coordinator inputCoordinator = new Coordinator();
		Coordinator outputCoordinator = new Coordinator();

		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Producer(inputCoordinator,
				INPUT_FILE_NAME));
		for (int i = 0; i < THREAD_COUNT; i++) {
			service.execute(new Processor(inputCoordinator, outputCoordinator));
		}
		service.execute(new Consumer(outputCoordinator,
				OUTPUT_FILE_NAME));
		//service.execute( new Monitor(inputCoordinator));
		service.shutdown();
	}
}
