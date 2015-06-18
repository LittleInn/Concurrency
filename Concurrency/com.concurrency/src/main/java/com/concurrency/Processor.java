package com.concurrency;

import java.util.Date;
import java.util.concurrent.Semaphore;


public class Processor implements Runnable {
	private FileService fileService;
	
	private String name;
//	private String element;

	public Processor(FileService fileService, String name) {
		this.fileService = fileService;
		this.name = name;
	}

	@Override
	public void run() {
		long start = new Date().getTime();
		while (!fileService.stopProcess) {
			String element = fileService.process();
			if(element != null){
				
				String reverseProcess = fileService.reverseProcess(element);
				fileService.writeProcess(reverseProcess);
			}
			System.out.println("Process "+name);
		}
		System.out.println("Processor "+name+ " finished at time: "+(new Date().getTime()-start));
	}

}