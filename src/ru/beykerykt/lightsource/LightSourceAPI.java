package ru.beykerykt.lightsource;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import ru.beykerykt.lightsource.items.ItemManager;
import ru.beykerykt.lightsource.items.flags.FlagManager;
import ru.beykerykt.lightsource.sources.SourceManager;

public class LightSourceAPI {

	private static ItemManager itemManager;
	private static FlagManager flagManager;
	private static SourceManager sourceManager;

	public static void log(ConsoleCommandSender sender, String message) {
		sender.sendMessage(ChatColor.AQUA + "[LightAPI]: " + ChatColor.WHITE + message);
	}

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
}
