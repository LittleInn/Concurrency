package com.concurrency;


public class Processor implements Runnable {
	private FileService fileService;
	private String name;

	public Processor(FileService fileService, String name) {
		this.fileService = fileService;
		this.name = name;
	}

	@Override
	public void run() {
		while (!fileService.stopProcess) {
			fileService.process();
			System.out.println("Processor "+name+ " finished");
		}
	}

}
