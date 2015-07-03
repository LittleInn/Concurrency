package com.multithread;

import com.general.GeneralCoordinator;

public class Monitor extends Thread {
	private GeneralCoordinator inputCoordinator;
	private GeneralCoordinator outputCoordinator;

	public Monitor(GeneralCoordinator inputCoordinator,
			GeneralCoordinator outputCoordinator) {
		super();
		this.outputCoordinator = outputCoordinator;
		this.inputCoordinator = inputCoordinator;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (!outputCoordinator.isStopApp()) {
			System.out
					.println("Read Lines: " + inputCoordinator.getReadLinesCount());
			System.out.println("Processed Lines: "
					+ outputCoordinator.getProcessedLinesCount());
			System.out.println("Written Lines: "
					+ outputCoordinator.getWrittenLinesCount());
			System.out.println("-----------------------------");
		}
	}
}
