package com.concurrency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.LinkedList;

public class FileService {
	 private static final String FILE_PATH = "D:\\Test_Files\\test.txt";
//	private static final String FILE_PATH = "D:\\Test_Files\\200.txt";
	private static final String OUTPUT_FILE_PATH = "D:\\Test_Files\\final.txt";
	
	private boolean bufferedFull;
	private boolean bufferedFullOutput = false;
	private int bufferSize;
	private LinkedList<String> inputQueue;
	private volatile LinkedList<String> outputQueue;
	private int threadNum;
	private int startPosition = 0;
	private int endPosition = 0;
	protected volatile boolean stopApp = false;
	protected volatile boolean stopWrite = false;
	protected volatile boolean stopProcess = false;
	private RandomAccessFile rw;

	public FileService(int threadNum, int bufferSize) {
		this.threadNum=threadNum;
		this.bufferSize = bufferSize;

	}

	public void init() {
		inputQueue = new LinkedList<String>();
		outputQueue = new LinkedList<String>(
				Collections.nCopies(bufferSize, ""));
		bufferedFull = false;
		try {
			rw = new RandomAccessFile(FILE_PATH, "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	public synchronized void process() {
		while (!bufferedFull) {
			try {
				System.out.println("process wait");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (inputQueue.isEmpty()) {
			stopProcess = true;
		} else {
			int size = inputQueue.size();
			int step = size / 4;
			endPosition += step;
			if (endPosition > size) {
				startPosition = endPosition - step;
				endPosition = endPosition - (endPosition - size);
			}
			System.out.println("process: " + startPosition + " : "
					+ endPosition);
			LinkedList<String> subList = new LinkedList<>(inputQueue.subList(
					startPosition, endPosition));
			int index = startPosition;
			while (!subList.isEmpty()) {
				String line = subList.poll();
				String newLine = line.toUpperCase() + "\n";
				outputQueue.set(index++, newLine);
			}
			threadNum--;
			startPosition += step;
			System.out.println("thread num: " + threadNum);
			if (threadNum == 0) {
				startPosition = 0;
				endPosition = 0;
				bufferedFull = false;
				bufferedFullOutput = true;
				inputQueue.clear();
				notifyAll();
				threadNum = 4;
			}
		}
	}

	public synchronized void readFile() {
		while (bufferedFull) {
			try {
				System.out.println("read wait");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			String currentLine;
			int lineCounter = 0;
			while ((currentLine = rw.readLine()) != null) {
				if (lineCounter == bufferSize) {
					break;
				}
				lineCounter++;
				inputQueue.add(currentLine);
			}
		} catch (IOException e) {
		}
		if (inputQueue.isEmpty()) {
			stopApp = true;
			try {
				rw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		bufferedFull = true;
		notifyAll();
	}

	public synchronized void writeFile() {
		while (!bufferedFullOutput) {
			System.out.println("write wait");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		File outputFile = new File(OUTPUT_FILE_PATH);
		try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
			while (!outputQueue.isEmpty()) {
				fileWriter.write(outputQueue.poll());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (stopApp) {
			stopWrite = true;
		} else {
			outputQueue.clear();
			outputQueue = new LinkedList<String>(Collections.nCopies(bufferSize, ""));
			bufferedFullOutput = false;
			notifyAll();
		}
	}
}
