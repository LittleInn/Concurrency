package com.lib.concurrency;


public class Consumer implements Runnable {
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
