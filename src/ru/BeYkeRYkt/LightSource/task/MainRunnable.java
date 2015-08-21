package ru.BeYkeRYkt.LightSource.task;

import java.util.Iterator;

public class MainRunnable implements Runnable {

	private TaskManager manager;

	public MainRunnable(TaskManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		if (!manager.getTasks().isEmpty()) {
			Iterator<Task> it = manager.getTasks().iterator();
			while (it.hasNext()) {
				Task i = it.next();
				if (i.isStarted()) {
					i.doTick();
				}
			}
		}
	}

}
