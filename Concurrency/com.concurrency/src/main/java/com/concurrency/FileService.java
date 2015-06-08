package com.concurrency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.LinkedList;

public class FileService {
	private static final String FILE_PATH = "D:\\Test_Files\\test.txt";
	private static final String OUTPUT_FILE_PATH = "D:\\Test_Files\\short.txt";
	private static final int POSITION = 0;
	private boolean bufferedFull;
	private boolean outputBufferFull;
	private int bufferSize;
	private LinkedList<String> inputQueue;
	private LinkedList<String> outputQueue;
	private int threadNum;
	private int threadAmounth;
	private int startPosition;
	private int endPosition;
	protected boolean stopApp = false;
	protected boolean stopWrite = false;
	protected boolean stopProcess = false;
	private RandomAccessFile rw;

	public FileService(int threadNum, int bufferSize) {
		this.threadNum = threadNum;
		this.threadAmounth = threadNum;
		this.bufferSize = bufferSize;

	}

	public void init() {
		inputQueue = new LinkedList<String>();
		outputQueue = new LinkedList<String>(
				Collections.nCopies(bufferSize, ""));
		bufferedFull = false;
		outputBufferFull = false;
		startPosition = POSITION;
		endPosition = POSITION;
		try {
			rw = new RandomAccessFile(FILE_PATH, "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processFileElement() {
		if (inputQueue.isEmpty()) {
			stopProcess = true;
		} else {
			processSubList();
			refreshProcessAction();
		}
	}

	private void processSubList() {
		int size = inputQueue.size();
		int step = size / getThreadAmounth();
		int over = size % getThreadAmounth();
		endPosition += step;
		endPosition = (threadNum == 1) ? (endPosition + over) : endPosition;
		if (endPosition > size) {
			startPosition = endPosition - step;
			endPosition = endPosition - (endPosition - size);
		}
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
	}

	public int getThreadAmounth() {
		return threadAmounth;
	}

	public void setThreadAmounth(int threadAmounth) {
		this.threadAmounth = threadAmounth;
	}

	private void refreshProcessAction() {
		if (threadNum == 0) {
			startPosition = POSITION;
			endPosition = POSITION;
			bufferedFull = false;
			outputBufferFull = true;
			inputQueue.clear();
			threadNum = getThreadAmounth();
			notifyAll();
		}
	}

	public synchronized void process() {
		while (!bufferedFull) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		processFileElement();
	}

	public synchronized void readFile() {
		while (bufferedFull) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		readInputFile();
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
		while (!outputBufferFull) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		writeOutputFile();

		if (stopApp) {
			stopWrite = true;
		} else {
			outputQueue.clear();
			outputQueue = new LinkedList<String>(Collections.nCopies(
					bufferSize, ""));
			outputBufferFull = false;
			notifyAll();
		}
	}

	private void readInputFile() {
		try {
			String currentLine;
			int lineCounter = 0;
			while ((currentLine = rw.readLine()) != null) {
				if (lineCounter == (bufferSize - 1)) {
					inputQueue.add(currentLine);
					break;
				}
				lineCounter++;
				inputQueue.add(currentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeOutputFile() {
		File outputFile = new File(OUTPUT_FILE_PATH);
		try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
			while (!outputQueue.isEmpty()) {
				fileWriter.write(outputQueue.poll());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
