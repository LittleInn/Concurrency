package com.multithread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer implements Runnable {

	private Coordinator outputCoordinator;
	private File outputFile;

	public Consumer(Coordinator outputCoordinator, String fileName) {
		super();
		this.outputCoordinator = outputCoordinator;
		outputFile = new File(fileName);
	}

	@Override
	public void run() {
		try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
			while (!outputCoordinator.isStopApp()) {
				synchronized (outputCoordinator) {
					while (outputCoordinator.isEmptyList()) {
						try {
							outputCoordinator.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (!outputCoordinator.getTasks().isEmpty()) {
						fileWriter.write(outputCoordinator.getTask()
								.getElement() + "\n");
					}
					if (outputCoordinator.getTasks().isEmpty()
							& outputCoordinator.isProcessEnd()) {
						outputCoordinator.setStopApp(true);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
