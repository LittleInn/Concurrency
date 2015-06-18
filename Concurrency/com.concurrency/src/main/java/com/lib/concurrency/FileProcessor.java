package com.lib.concurrency;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class FileProcessor {
	private static final String FILE_PATH = "D:\\Test_Files\\test.txt";
	private int bufferSize;
	private BlockingQueue<String> inputQueue;
//	private LinkedList<String> inputQueue;
	private RandomAccessFile rw;
	private CountDownLatch countDownLatch;
	private CyclicBarrier readBarrier;
	private boolean stopApp;

	public FileProcessor(CountDownLatch countDownLatch, int bufferSize, CyclicBarrier readBarrier) {
		super();
		this.countDownLatch = countDownLatch;
		this.bufferSize=bufferSize;
		this.readBarrier=readBarrier;
	}

	public void init() {
		inputQueue = new LinkedBlockingQueue<String>();
		try {
			rw = new RandomAccessFile(FILE_PATH, "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readFile() {
		readInputFile();
	}

	private void readInputFile() {
		//readBarrier.reset();
		try {
			String currentLine;
			int lineCounter = 0;
			while ((currentLine = rw.readLine()) != null) {
				if((currentLine = rw.readLine()) == null){
					setStopApp(true);
				}
				if (lineCounter == (bufferSize - 1)) {
					inputQueue.add(currentLine);
					//countDownLatch.countDown();
					break;
				}
				lineCounter++;
				//countDownLatch.countDown();
				System.out.println("Line: "+currentLine);
//				System.out.println("Count: " + countDownLatch.getCount());
				if(currentLine!=null){
					
					inputQueue.add(currentLine);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void processLine(){
		while(!inputQueue.isEmpty()){
			System.out.println("````````````````````````````````Thread: "+Thread.currentThread().getName());
		System.out.println("Process line: "+inputQueue.poll());
		}
	}
	
	public void consumeFile(){
		System.out.println("Queue: "+inputQueue);
	}

	public boolean isStopApp() {
		return stopApp;
	}

	public void setStopApp(boolean stopApp) {
		this.stopApp = stopApp;
	}

}
