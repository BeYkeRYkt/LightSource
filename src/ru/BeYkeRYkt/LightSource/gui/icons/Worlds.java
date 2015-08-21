package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;

public class Worlds extends Icon {

	public Worlds() {
		super("worlds", Material.GRASS);
		setName(ChatColor.GREEN + "Worlds");
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Menu menu = LightSource.getInstance().getGUIManager().getMenuFromId("worldsMenu");
		LightSource.getInstance().getGUIManager().openMenu(player, menu);
		player.playSound(player.getLocation(), Sound.SHEEP_IDLE, 1, 1);
	}
}
