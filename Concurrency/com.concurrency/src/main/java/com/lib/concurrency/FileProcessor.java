package com.lib.concurrency;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
public class FileProcessor {
	private static final String FILE_PATH = "D:\\Test_Files\\test1.txt";
	private int bufferSize;
	private BlockingQueue<String> inputQueue;
	private RandomAccessFile rw;
	private boolean stopApp;

	public FileProcessor(int bufferSize) {
		super();
		this.bufferSize = bufferSize;
	}

	public void init() {
		inputQueue = new LinkedBlockingQueue<String>();
		try {
			rw = new RandomAccessFile(FILE_PATH, "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readFile() throws InterruptedException {
		readInputFile();
	}

	private void readInputFile() throws InterruptedException {
		try {
			String currentLine;
			int lineCounter = 0;
			while ((currentLine = rw.readLine()) != null) {
				if (lineCounter == (bufferSize - 1)) {
					inputQueue.put(currentLine);
					break;
				}
				lineCounter++;
				inputQueue.put(currentLine);
				System.out.println("Line: " + currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processLine() throws InterruptedException {
		while (!inputQueue.isEmpty()) {
			// System.out.println("````````````````````````````````Thread: "+Thread.currentThread().getName());
			System.out.println("Process line: " + inputQueue.take());
		}
	}

	public void consumeFile() {
		System.out.println("Queue: " + inputQueue);
	}

	public boolean isStopApp() {
		return stopApp;
	}

	public void setStopApp(boolean stopApp) {
		this.stopApp = stopApp;
	}

}
