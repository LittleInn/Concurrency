package com.concurrency;

public class Producer implements Runnable {
	private FileService fileService;

	public Producer(FileService fileService) {
		this.fileService = fileService;
	}

	@Override
	public void run() {
		while (!fileService.stopApp) {
			fileService.readFile();
		}
	}
}
