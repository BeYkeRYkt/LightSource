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
package ru.beykerykt.lightsource;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import ru.beykerykt.lightsource.items.ItemManager;
import ru.beykerykt.lightsource.items.flags.FlagManager;
import ru.beykerykt.lightsource.sources.SourceManager;
import ru.beykerykt.lightsource.sources.search.SearchMachine;

public class LightSourceAPI {

	private static ItemManager itemManager;
	private static FlagManager flagManager;
	private static SourceManager sourceManager;
	private static SearchMachine searchMachine;

	public static ItemManager getItemManager() {
		if (itemManager == null) {
			itemManager = new ItemManager();
		}
		return itemManager;
	}

	public static FlagManager getFlagManager() {
		if (flagManager == null) {
			flagManager = new FlagManager();
		}
		return flagManager;
	}

	public static SourceManager getSourceManager() {
		if (sourceManager == null) {
			sourceManager = new SourceManager();
		}
		return sourceManager;
	}

	public static SearchMachine getSearchMachine() {
		if (searchMachine == null) {
			searchMachine = new SearchMachine();
		}
		return searchMachine;
	}

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.AQUA + "[LightSource]: " + ChatColor.WHITE + message);
	}
}
