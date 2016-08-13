package ru.beykerykt.lightsource.items.flags;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import ru.beykerykt.lightsource.LightSourceAPI;

public class FlagManager {

	private Map<String, FlagExecutor> executors;

	public FlagManager() {
		this.executors = new ConcurrentHashMap<String, FlagExecutor>();
	}

	public Map<String, FlagExecutor> getFlags() {
		return executors;
	}

	public boolean registerFlag(String idKey, FlagExecutor executor) {
		if (executors.containsKey(idKey)) {
			LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "This flag is already in the list.");
			return false;
		}
		executors.put(idKey, executor);
		LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "Registered new flag: " + ChatColor.YELLOW + idKey);
		return true;
	}

	public boolean unregisterFlag(String idKey) {
		if (!executors.containsKey(idKey)) {
			LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "This id is not on the list.");
			return false;
		}
		executors.remove(idKey);
		LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "Removed flag: " + ChatColor.YELLOW + idKey);
		return true;
	}

	public FlagExecutor getFlag(String idKey) {
		if (!hasFlag(idKey)) {
			LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "This id is not on the list: " + ChatColor.YELLOW + idKey);
		}
		return executors.get(idKey);
	}

	public boolean hasFlag(String idKey) {
		return executors.containsKey(idKey);
	}
}
