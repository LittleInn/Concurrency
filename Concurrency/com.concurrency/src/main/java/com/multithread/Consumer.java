package com.multithread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer implements Runnable {

	private Coordinator engine;
	private File outputFile;
	
	

	public Consumer(Coordinator engine, String fileName) {
		super();
		this.engine = engine;
		outputFile = new File(fileName);
	}

	@Override
	public void run() {
		boolean stopWrite = false;
		while (!stopWrite) {
			synchronized (engine) {
				while (engine.emptyList) {
					try {
						engine.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
					if (!engine.tasks.isEmpty()) {
						fileWriter.write(engine.getTask().getElement() + "\n");
					}
					if(engine.tasks.isEmpty() & engine.isProcessend()){
						stopWrite=true;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
