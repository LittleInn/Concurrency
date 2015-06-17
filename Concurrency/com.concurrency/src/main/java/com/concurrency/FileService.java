package com.concurrency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class FileService {
	private static final String FILE_PATH = "D:\\Test_Files\\2G.txt";
	private static final String OUTPUT_FILE_PATH = "D:\\Test_Files\\result2G.txt";
	private boolean bufferedFull;
	private boolean outputBufferFull;
	private int bufferSize;
	private LinkedList<String> inputQueue;
	private LinkedList<String> outputQueue;
	protected boolean stopApp = false;
	protected boolean stopWrite = false;
	protected boolean stopProcess = false;
	private RandomAccessFile rw;

	public FileService(int bufferSize) {
		this.bufferSize = bufferSize;

	}

	public void init() {
		inputQueue = new LinkedList<String>();
		// outputQueue = new LinkedList<String>(
		// Collections.nCopies(bufferSize, ""));
		outputQueue = new LinkedList<String>();
		bufferedFull = false;
		outputBufferFull = false;
		try {
			rw = new RandomAccessFile(FILE_PATH, "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public synchronized String process() {
		while (!bufferedFull) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		String element = inputQueue.poll();
		if (inputQueue.isEmpty()) {
			bufferedFull = false;
			notifyAll();
		}
		return element;
	}

	public String reverseProcess(String element) {
		return new StringBuffer(element).reverse().toString() + "\n";
	}

	public synchronized void writeProcess(String line) {
		outputQueue.add(line);
		outputBufferFull = true;
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
			stopProcess = true;
		} else {
			outputBufferFull = false;
			notifyAll();
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
