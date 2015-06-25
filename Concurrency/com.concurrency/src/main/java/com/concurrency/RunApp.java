package com.concurrency;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.general.Monitor;


public class RunApp {
	private static final int THREAD_COUNT = 10;
	private final static String INPUT_FILE_NAME = "D:\\Test_Files\\test.txt";
	private final static String OUTPUT_FILE_NAME = "D:\\Test_Files\\555R.txt";

	public static void main(String[] args) {
		System.out.println("START: " + new Date().getTime());
		Coordinator coordinator = new Coordinator();

		ExecutorService service = Executors
				.newFixedThreadPool(THREAD_COUNT + 3);
		service.execute(new Producer(coordinator, INPUT_FILE_NAME));
		for (int i = 0; i < THREAD_COUNT; i++) {
			service.execute(new Processor(coordinator));
		}
		service.execute(new Consumer(coordinator, OUTPUT_FILE_NAME));
		service.execute(new Monitor(coordinator));
		service.shutdown();
	}
}
