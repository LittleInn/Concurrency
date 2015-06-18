package com.lib.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Processor implements Runnable {
	private CountDownLatch countDownLatch;
	private CyclicBarrier barrier;
	private FileProcessor fileProcessor;

	public Processor(CyclicBarrier barrier, FileProcessor fileProcessor, CountDownLatch countDownLatch) {
		super();
		this.barrier = barrier;
		this.fileProcessor=fileProcessor;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
//		try {
//			//countDownLatch.await();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileProcessor.processLine();

	}

}
