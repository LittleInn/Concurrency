package com.lib.concurrency;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunApp {
	private static final int BUFFER_SIZE = 5;

	public static void main(String[] args) {
		FileProcessor fileProcessor = new FileProcessor(BUFFER_SIZE);
		CyclicBarrier processbarrier = new CyclicBarrier(3, new Consumer(
				fileProcessor));
		fileProcessor.init();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Producer(fileProcessor));
		Processor processor = new Processor(processbarrier, fileProcessor);
		Processor processor2 = new Processor(processbarrier, fileProcessor);
		Processor processor3 = new Processor(processbarrier, fileProcessor);
		service.execute(processor);
		service.execute(processor2);
		service.execute(processor3);
		service.shutdown();
	}
}
