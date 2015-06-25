package com.concurrency;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import com.concurrency.Coordinator;

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
		coordinator.setStartTime(new Date().getTime());
		try {
			readFile();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void readFile() throws InterruptedException {
		String currentLine;
		try (RandomAccessFile rw = new RandomAccessFile(getFileName(), "r")) {
			while (!coordinator.isEndOfFile()) {
					try {
						currentLine = rw.readLine();
						if (currentLine != null) {
							coordinator.addTask(coordinator.getTasksForProcess(), currentLine);
						} else {
							coordinator.setEndOfFile(true);
						}
					} catch (IOException e) {
						e.printStackTrace();
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
