package ru.beykerykt.lightsource.items.flags;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlagManager {

	private Map<String, FlagExecutor> executors;

	public FlagManager() {
		this.executors = new ConcurrentHashMap<String, FlagExecutor>();
	}

	public Map<String, FlagExecutor> getFlags() {
		return executors;
	}

	public boolean registerFlag(String idKey, FlagExecutor executor) {
		if (executors.containsKey(idKey))
			return false;
		executors.put(idKey, executor);
		return true;
	}

	public boolean unregisterFlag(String idKey) {
		if (!executors.containsKey(idKey))
			return false;
		executors.remove(idKey);
		return true;
	}

	public FlagExecutor getFlag(String id) {
		FlagExecutor tag = executors.get(id);
		return tag;
	}
}
