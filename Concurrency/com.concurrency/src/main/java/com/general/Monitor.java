package com.general;

import java.util.Date;
import java.util.Set;

public class Monitor extends Thread{
	private GeneralCoordinator coordinator;

	public Monitor(GeneralCoordinator coordinator) {
		super();
		this.coordinator = coordinator;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (!coordinator.isStopApp()) {
			Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			for (Thread thread : threadSet) {
				System.out.println("RUNNING THREAD: " + thread.getName()+" : "+thread.getState().name());
			}
		}
		long durationTime = new Date().getTime()-coordinator.getStartTime();
		System.out.println("Task end at "+ durationTime+" ms");
	}
}
