package com.concurrency;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import com.concurrency.Coordinator;

public class Producer implements Runnable {
	private Coordinator coordinator;
	private String fileName;
	private String currentLine;
	private long currentLineCount = 0;

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
		try (RandomAccessFile rw = new RandomAccessFile(getFileName(), "r")) {
			produceInputData(rw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void produceInputData(RandomAccessFile rw)
			throws InterruptedException {
		while (!coordinator.isEndOfFile()) {
			try {
				writeTaskForProcess(rw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void writeTaskForProcess(RandomAccessFile rw)
			throws InterruptedException, IOException {
		currentLine = rw.readLine();
		if (currentLine != null) {
			coordinator.addTask(coordinator.getTasksForProcess(), currentLine);
			coordinator.setReadLinesCount(currentLineCount++);
		} else {
			coordinator.setEndOfFile(true);
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
