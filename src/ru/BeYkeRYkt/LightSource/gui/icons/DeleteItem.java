package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.editor.PlayerEditor;
import ru.BeYkeRYkt.LightSource.items.ItemManager;

public class DeleteItem extends Icon {

	public DeleteItem() {
		super("deleteItem", Material.CAULDRON_ITEM);
		setName(ChatColor.RED + "Delete item");
	}

	@Override
	public void onItemClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		PlayerEditor editor = LightSource.getInstance().getEditorManager().getEditor(player.getName());
		try {
			ItemManager.removeLightSource(editor.getItem());
			LightSource.getInstance().log(player, ChatColor.GREEN + "Item successfully deleted !");
			LightSource.getInstance().log(player, ChatColor.DARK_AQUA + "Refreshing GUI Manager...");
			LightSource.getInstance().getGUIManager().refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		LightSource.getInstance().getGUIManager().openMenu(player, LightSource.getInstance().getGUIManager().getMenuFromId("page_0"));
	}

}
