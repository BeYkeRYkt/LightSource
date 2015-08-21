package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.editor.PlayerCreator;

public class CreateItem extends Icon {

	public CreateItem() {
		super("createItem", Material.ANVIL);
		setName(ChatColor.GOLD + "Create new item");
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (!LightSource.getInstance().getEditorManager().isCreator(player.getName())) {
			PlayerCreator creator = new PlayerCreator(player.getName());
			LightSource.getInstance().getEditorManager().addCreator(creator);

			LightSource.getInstance().log(player, "Enter item id " + ChatColor.YELLOW + "(Example: MyBestItem or MYBESTITEMTOO) ");
			player.closeInventory();
		}
	}

}
