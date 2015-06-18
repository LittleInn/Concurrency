package com.lib.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Producer implements Runnable {
	private CountDownLatch countDownLatch;
	private CyclicBarrier barrier;
	private FileProcessor fileProcessor;

	public Producer(CountDownLatch countDownLatch, FileProcessor fileProcessor) {
		super();
		this.countDownLatch = countDownLatch;
		this.fileProcessor = fileProcessor;
	}

	@Override
	public void run() {
		fileProcessor.readFile();
	}

}
