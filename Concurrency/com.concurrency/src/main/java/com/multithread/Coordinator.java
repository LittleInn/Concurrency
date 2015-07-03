package com.multithread;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.general.GeneralCoordinator;
import com.general.Task;

public class Coordinator extends GeneralCoordinator {

	private List<Task> tasks;

	public Coordinator() {
		super();
		tasks = new LinkedList<Task>();
	}

	public Task getTask() {
		Task element = null;
		synchronized (tasks) {
			while (isEmptyList()) {
				try {
					tasks.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Iterator<Task> iterator = tasks.iterator();
			element = null;
			if (iterator.hasNext()) {
				element = iterator.next();
			}
			tasks.remove(element);
		}
		return element;
	}

	public void addTask(String element) {
		synchronized (tasks) {
			tasks.add(new Task(element));
			setEmptyList(false);
			tasks.notify();
		}
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
