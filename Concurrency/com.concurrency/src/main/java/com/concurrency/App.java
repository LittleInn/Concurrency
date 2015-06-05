package com.concurrency;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 * 
 */
public class App {
	private static final int THREAD_COUNT = 4;
	private static final int BUFFER_SIZE = 100;

	public static void main(String[] args) {
		FileService fileService = new FileService(THREAD_COUNT, BUFFER_SIZE);
		fileService.init();
		 Producer producer = new Producer(fileService);
				 Processor processor1 = new Processor(fileService, "First");
				 Processor processor2 = new Processor(fileService, "Second");
				 Processor processor3 = new Processor(fileService, "Third");
				 Processor processor4 = new Processor(fileService, "Four");
				 Consumer consumer = new Consumer(fileService);
				
				 Thread prod = new Thread(producer);
				 Thread process1 = new Thread(processor1);
				 Thread process2 = new Thread(processor2);
				 Thread process3 = new Thread(processor3);
				 Thread process4 = new Thread(processor4);
				 Thread consum = new Thread(consumer);
				 prod.start();
				 process1.start();
				 process2.start();
				 process3.start();
				 process4.start();
				 consum.start();
		
//		long start = new Date().getTime();
//
//		ExecutorService service = Executors.newCachedThreadPool();
//		service.execute(new Producer(fileService));
//		for (int i = 0; i < THREAD_COUNT; i++) {
//			service.execute(new Processor(fileService, "Thread# " + i));
//		}
//		service.execute(new Consumer(fileService));
//		long end = new Date().getTime();
//		System.out.println("TIME: "+(end-start));
	}
}
