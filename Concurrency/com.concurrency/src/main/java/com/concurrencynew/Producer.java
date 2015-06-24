package com.concurrencynew;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.concurrencynew.Coordinator;

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
		//System.out.println("read run");
		try {
			readFile();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Read end!!!");
	}

	private void readFile() throws InterruptedException {
		String currentLine;
		try (RandomAccessFile rw = new RandomAccessFile(getFileName(), "r")) {
			while (!coordinator.isEndOfFile()) {
				synchronized (coordinator) {
					try {
						currentLine = rw.readLine();
						if (currentLine != null) {
							coordinator.addTask(currentLine);
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
