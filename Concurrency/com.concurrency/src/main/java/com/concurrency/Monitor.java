package com.concurrency;

import com.general.GeneralCoordinator;

public class Monitor extends Thread {
	private GeneralCoordinator coordinator;

	public Monitor(GeneralCoordinator coordinator) {
		super();
		this.coordinator = coordinator;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (!coordinator.isStopApp()) {
			System.out
					.println("Read Lines: " + coordinator.getReadLinesCount());
			System.out.println("Processed Lines: "
					+ coordinator.getProcessedLinesCount());
			System.out.println("Written Lines: "
					+ coordinator.getWrittenLinesCount());
			System.out.println("---------------------------------");
		}
	}
}
