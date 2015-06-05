package com.concurrency;

public class Consumer implements Runnable {
	private FileService fileService;

	public Consumer(FileService fileService) {
		this.fileService = fileService;
	}

	@Override
	public void run() {
		while (!fileService.stopWrite) {
			fileService.writeFile();
			System.out.println("DONE");
		}
	}

}
