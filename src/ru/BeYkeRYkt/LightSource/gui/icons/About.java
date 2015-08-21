package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;

public class About extends Icon {

	public About() {
		super("about", Material.BOOK);
		setName(ChatColor.GREEN + "About");
		getLore().add("");
		getLore().add(ChatColor.WHITE + "Version: " + LightSource.getInstance().getDescription().getVersion());
		getLore().add(ChatColor.WHITE + "Author: " + ChatColor.GREEN + LightSource.getInstance().getDescription().getAuthors().get(0));
		getLore().add(ChatColor.AQUA + "Click here for reload plugin.");
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		player.playSound(player.getLocation(), Sound.HORSE_IDLE, 1, 1);
		try {
			// LightSource.getInstance().onDisable();
			// LightSource.getInstance().onEnable();
			Bukkit.getPluginManager().disablePlugin(LightSource.getInstance());
			Bukkit.getPluginManager().enablePlugin(LightSource.getInstance());
			player.closeInventory();
			LightSource.getInstance().log(player, ChatColor.GREEN + "Reloading is complete.");
		} catch (Exception e) {
			LightSource.getInstance().log(player, ChatColor.RED + "There was an error when you restart the plugin, send to developer log.");
			e.printStackTrace();
		}
	}
}
