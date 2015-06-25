package com.concurrency;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Consumer implements Runnable {
	private Coordinator coordinator;
	private File outputFile;

	public Consumer(Coordinator engine, String fileName) {
		super();
		this.coordinator = engine;
		outputFile = new File(fileName);
	}

	@Override
	public void run() {
		try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
		while (!coordinator.isStopApp()) {
				if (!coordinator.getTasksProcessed().isEmpty()) {
					try {
						fileWriter.write(coordinator.getTask(coordinator.getTasksProcessed()).getElement()
								+ "\n");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (coordinator.getTasksProcessed().isEmpty()
						& coordinator.isProcessEnd()) {
					coordinator.setStopApp(true);
				}
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
