package com.multithread;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OutputEngine {

	List<Task> processedTask = new LinkedList<Task>();
	boolean outputListFull = false;

	public synchronized void addProcessedTask(String element) {
		processedTask.add(new Task(element));
	}

	public synchronized Task getTask(){
		Iterator<Task> iterator = processedTask.iterator();
		Task task = null;
		if(iterator.hasNext()){
			task = iterator.next();
		}
		processedTask.remove(task);
		return task;
	}
}
