package com.lib.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Processor implements Runnable {
	private CyclicBarrier barrier;
	private FileProcessor fileProcessor;

	public Processor(CyclicBarrier barrier, FileProcessor fileProcessor) {
		super();
		this.barrier = barrier;
		this.fileProcessor = fileProcessor;
	}

	@Override
	public void run() {
		try {
			barrier.await();
			fileProcessor.processLine();
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
