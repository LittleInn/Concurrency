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
		Iterator<Task> iterator = tasks.iterator();
		Task element = null;
		if (iterator.hasNext()) {
			element = iterator.next();
		}
		tasks.remove(element);
		return element;
	}

	public void addTask(String element) {
		tasks.add(new Task(element));
		setEmptyList(false);
		// emptyList = false;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

}
