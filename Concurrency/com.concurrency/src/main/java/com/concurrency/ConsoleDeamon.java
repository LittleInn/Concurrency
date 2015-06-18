package com.concurrency;


public class ConsoleDeamon implements Runnable {
	private FileService fileService;

	public ConsoleDeamon(FileService fileService) {
		this.fileService = fileService;
	}

	@Override
	public void run() {
		
	}

}
