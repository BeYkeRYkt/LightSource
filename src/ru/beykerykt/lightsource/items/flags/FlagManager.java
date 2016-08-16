/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 - 2016
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
