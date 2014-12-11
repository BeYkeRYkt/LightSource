package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class EntityLight extends Icon {

	public EntityLight() {
		super("entityLight", Material.SKULL_ITEM);
		setName(ChatColor.GREEN + "EntityLight");
		getLore().add("");
		getLore().add(ChatColor.DARK_RED + "WARNING!");
		getLore().add(ChatColor.RED + "This option can cause colossal lags!");
		getLore().add(ChatColor.GOLD + "Status: ");
		getLore().add(String.valueOf(LightSource.getInstance().getDB().isEntityLight()));
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 1, 1);
		Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("mainMenu");
		LightSource.getAPI().getGUIManager().openMenu(player, menu);
	}
}