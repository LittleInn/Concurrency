package com.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.general.AppManagment;

public class RunApp {
	private static final int GENERAL_THREAD_COUNT = 2;

	private static void runApp() {
		Coordinator coordinator = new Coordinator();

		Thread threadMonitor = new Monitor(coordinator);
		threadMonitor.start();

		ExecutorService service = Executors.newFixedThreadPool(AppManagment
				.getThreadCount() + GENERAL_THREAD_COUNT);
		service.execute(new Producer(coordinator, AppManagment.getInputFile()));

		for (int i = 0; i < AppManagment.getThreadCount(); i++) {
			service.execute(new Processor(coordinator));
		}
		
		service.execute(new Consumer(coordinator, AppManagment.getOutputFile()));
		service.shutdown();
	}

	public static void main(String[] args) {
		RunApp.runApp();
	}
}
