package com.multithread;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Producer implements Runnable {
	private Coordinator coordinator;
	private String fileName;

	public Producer(Coordinator coordinator, String fileName) {
		super();
		this.coordinator = coordinator;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		readFile();
	}

	private void readFile() {
		String currentLine;
		try (RandomAccessFile rw = new RandomAccessFile(getFileName(), "r")) {
			while (!coordinator.isEndOfFile()) {
				synchronized (coordinator) {
					try {
						currentLine = rw.readLine();
						if (currentLine != null) {
							coordinator.addTask(currentLine);
							coordinator.notify();
						} else {
							coordinator.setEndOfFile(true);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
