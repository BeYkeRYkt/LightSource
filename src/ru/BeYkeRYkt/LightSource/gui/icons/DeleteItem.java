package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightAPI;
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
        PlayerEditor editor = LightAPI.getEditorManager().getEditor(player.getName());
        try {
            ItemManager.removeLightSource(editor.getItem());
            LightSource.getAPI().log(player, ChatColor.GREEN + "Item successfully deleted !");
            LightSource.getAPI().log(player, ChatColor.DARK_AQUA + "Refreshing GUI Manager...");
            LightSource.getAPI().getGUIManager().refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LightSource.getAPI().getGUIManager().openMenu(player, LightSource.getAPI().getGUIManager().getMenuFromId("page_0"));
    }

}