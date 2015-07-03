package com.multithread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer implements Runnable {

	private Coordinator outputCoordinator;
	private File outputFile;
	private long writtenLinesCount = 0;

	public Consumer(Coordinator outputCoordinator, String fileName) {
		super();
		this.outputCoordinator = outputCoordinator;
		outputFile = new File(fileName);
	}

	@Override
	public void run() {
		writeFile();
	}

	private void writeFile() {
		try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
			while (!outputCoordinator.isStopApp()) {
				consumeInputData(fileWriter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void consumeInputData(FileWriter fileWriter) throws IOException {
		if (!outputCoordinator.getTasks().isEmpty()) {
			writeLine(fileWriter);
			outputCoordinator.setWrittenLinesCount(writtenLinesCount++);
		}
		if (outputCoordinator.getTasks().isEmpty()
				& outputCoordinator.isProcessEnd()) {
			outputCoordinator.setStopApp(true);
		}
	}

	private void writeLine(FileWriter fileWriter) throws IOException {
		fileWriter.write(outputCoordinator.getTask().getElement() + "\n");

	}

}
