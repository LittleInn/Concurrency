package com.lib.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunApp {
	private static final int BUFFER_SIZE = 100;

	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(BUFFER_SIZE);
		CyclicBarrier readBarrier = new CyclicBarrier(1);
		FileProcessor fileProcessor = new FileProcessor(countDownLatch,
				BUFFER_SIZE, readBarrier);
		fileProcessor.init();
		CyclicBarrier processbarrier = new CyclicBarrier(1, new Consumer(
				fileProcessor));
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Producer(countDownLatch, fileProcessor));
		// for (int i = 0; i < THREAD_COUNT; i++) {
		// service.execute(new Processor(fileService, "Thread# " + i));
		// }
		Processor processor = new Processor(processbarrier, fileProcessor,
				countDownLatch);
//		Processor processor2 = new Processor(processbarrier, fileProcessor,
//				countDownLatch);
//		Processor processor3 = new Processor(processbarrier, fileProcessor,
//				countDownLatch);
//		service.execute(processor2);
//		service.execute(processor3);
		service.execute(processor);
		// service.execute(new Consumer(fileService));
		service.shutdown();
	}
}
