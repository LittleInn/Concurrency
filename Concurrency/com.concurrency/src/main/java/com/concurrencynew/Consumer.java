package com.concurrencynew;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer implements Runnable {
	private Coordinator outputCoordinator;
	private File outputFile;

	public Consumer(Coordinator engine, String fileName) {
		super();
		this.outputCoordinator = engine;
		outputFile = new File(fileName);
	}

	@Override
	public void run() {
		boolean stopWrite = false;
		try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
		while (!stopWrite) {
				if (!outputCoordinator.getTasks().isEmpty()) {
					try {
						fileWriter.write(outputCoordinator.getTask().getElement()
								+ "\n");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (outputCoordinator.getTasks().isEmpty()
						& outputCoordinator.isProcessEnd()) {
					stopWrite = true;
				}
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("PROCESS END");
	}
}
