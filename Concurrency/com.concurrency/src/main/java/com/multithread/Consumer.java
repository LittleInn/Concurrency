package com.multithread;

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
				synchronized (outputCoordinator) {
					while (outputCoordinator.emptyList) {
						try {
							outputCoordinator.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (!outputCoordinator.tasks.isEmpty()) {
						System.out.println("write: ");
						fileWriter.write(outputCoordinator.getTask()
								.getElement() + "\n");
//						fileWriter.flush();
					}
					if (outputCoordinator.tasks.isEmpty()
							& outputCoordinator.isProcessEnd()) {
						stopWrite = true;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("consume end " + Thread.currentThread().getName());
	}

}
