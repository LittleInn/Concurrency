package com.concurrency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Producer implements Runnable {
	private FileService fileService;

	public Producer(FileService fileService) {
		this.fileService = fileService;
	}

	@Override
	public void run() {
		while(!fileService.stopApp){
		fileService.readFile();}
	}
}
