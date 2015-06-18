package com.lib.concurrency;

public class Producer implements Runnable {
	private FileProcessor fileProcessor;

	public Producer(FileProcessor fileProcessor) {
		super();
		this.fileProcessor = fileProcessor;
	}

	@Override
	public void run() {

			try {
				fileProcessor.readFile();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}
