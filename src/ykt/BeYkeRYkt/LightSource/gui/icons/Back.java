package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class Back extends Icon {

	public Back() {
		super("back", Material.WORKBENCH);

		setName(ChatColor.GREEN + "Back");
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		Menu menu1 = LightSource.getAPI().getGUIManager().getMenuFromId("mainMenu");
		Player player = (Player) event.getWhoClicked();
		LightSource.getAPI().getGUIManager().openMenu(player, menu1);
	}
}