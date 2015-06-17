package com.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
	private static final int THREAD_COUNT = 10;
	private static final int BUFFER_SIZE = 1000;

	public static void main(String[] args) {
		FileService fileService = new FileService(BUFFER_SIZE);
		fileService.init();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Producer(fileService));
		for (int i = 0; i < THREAD_COUNT; i++) {
			service.execute(new Processor(fileService, "Thread# " + i));
		}
		service.execute(new Consumer(fileService));
	}
}
