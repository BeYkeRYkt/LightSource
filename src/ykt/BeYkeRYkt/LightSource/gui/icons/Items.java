package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;

public class Items extends Icon {

	public Items() {
		super("items", Material.CHEST);
		setName(ChatColor.GREEN + "Items");
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		LightSource.getAPI().getGUIManager().openMenu(player, LightSource.getAPI().getGUIManager().getMenuFromId("itemsMenu"));
	}
}