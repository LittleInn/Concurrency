package com.concurrencynew;

import java.util.Iterator;
import java.util.Set;

public class Monitor extends Thread{
	private Coordinator coordinator;

	public Monitor(Coordinator coordinator) {
	super();
	this.coordinator = coordinator;
	setDaemon(true);
}

	@Override
	public void run() {
		while(true){
			
		System.out.println("CURRENT!!!!!!!!!!!! "+coordinator.isProcessEnd());
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		Iterator<Thread> iterator = threadSet.iterator();
		for(Thread thread: threadSet){
			System.out.println("RUNNING THREAD: "+thread.getName());
			System.out.println("STATE THREAD: "+thread.getState().name());
			System.out.println("ALIVE THREAD: "+thread.isAlive());
		}
		}
	}
}
