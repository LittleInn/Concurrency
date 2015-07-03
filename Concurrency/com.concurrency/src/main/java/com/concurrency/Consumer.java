package com.concurrency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer implements Runnable {
	private Coordinator coordinator;
	private File outputFile;
	private long linesCount = 0;

	public Consumer(Coordinator engine, String fileName) {
		super();
		this.coordinator = engine;
		outputFile = new File(fileName);
	}

	@Override
	public void run() {
		writeFile();
	}

	private void writeFile() {
		try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
			while (!coordinator.isStopApp()) {
				consumeInputData(fileWriter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void consumeInputData(FileWriter fileWriter) throws IOException {
		if (!coordinator.getTasksProcessed().isEmpty()) {
			writeLine(fileWriter);
			coordinator.setWrittenLinesCount(linesCount++);
		}
		if (coordinator.getTasksProcessed().isEmpty()
				& coordinator.isProcessEnd()) {
			coordinator.setStopApp(true);
		}
	}

	private void writeLine(FileWriter fileWriter) throws IOException {
		try {
			fileWriter.write(coordinator.getTask(
					coordinator.getTasksProcessed()).getElement()
					+ "\n");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
