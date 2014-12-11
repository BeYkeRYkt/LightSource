package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;

public class About extends Icon {

	public About() {
		super("about", Material.BOOK);
		setName(ChatColor.GREEN + "About");
		getLore().add("");
		getLore().add(ChatColor.WHITE + "Version: " + LightSource.getInstance().getDescription().getVersion());
		getLore().add(ChatColor.WHITE + "Author: " + ChatColor.GREEN + LightSource.getInstance().getDescription().getAuthors().get(0));
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		player.playSound(player.getLocation(), Sound.HORSE_IDLE, 1, 1);
	}
}