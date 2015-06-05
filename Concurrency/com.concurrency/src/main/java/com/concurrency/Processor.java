package com.concurrency;

import java.util.LinkedList;
import java.util.Queue;

public class Processor implements Runnable {
	private FileService fileService;
	private String name;

	public Processor(FileService fileService, String name) {
		this.fileService = fileService;
		this.name=name;
	}

	@Override
	public void run() {
		while(!fileService.stopProcess){
			
			fileService.process();
			System.out.println("--------------------------------------------------Processor "+name+" DONE !!!");
		}
	}

}
