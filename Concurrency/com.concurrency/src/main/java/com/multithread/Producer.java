package com.multithread;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

public class Producer implements Runnable {
	private Coordinator coordinator;
	private String fileName;
	private String currentLine;
	private int lines = 0;

	public Producer(Coordinator coordinator, String fileName) {
		super();
		this.coordinator = coordinator;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		coordinator.setStartTime(new Date().getTime());
		readFile();
	}

	private void readFile() {
		try (RandomAccessFile rw = new RandomAccessFile(getFileName(), "r")) {
			produceInputData(rw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void produceInputData(RandomAccessFile rw) {
		while (!coordinator.isEndOfFile()) {
			try {
				writeTaskForProcess(rw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private int writeTaskForProcess(RandomAccessFile rw) throws IOException {
		currentLine = rw.readLine();
		if (currentLine != null) {
			coordinator.addTask(currentLine);
			coordinator.setReadLinesCount(lines++);
		} else {
			coordinator.setEndOfFile(true);
		}
		return lines;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
