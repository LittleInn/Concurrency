package com.lib.concurrency;

import java.util.concurrent.CyclicBarrier;

public class Consumer implements Runnable{
	private CyclicBarrier barrier;
	private FileProcessor fileProcessor;
	
	public Consumer(FileProcessor fileProcessor) {
		super();
		this.fileProcessor = fileProcessor;
	}

	@Override
	public void run() {
		fileProcessor.consumeFile();
	}

}
