package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.editor.PlayerCreator;
import ykt.BeYkeRYkt.LightSource.gui.Icon;

public class CreateItem extends Icon {

    public CreateItem() {
        super("createItem", Material.ANVIL);
        setName(ChatColor.GOLD + "Create new item");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!LightAPI.getEditorManager().isCreator(player.getName())) {
            PlayerCreator creator = new PlayerCreator(player.getName());
            LightAPI.getEditorManager().addCreator(creator);

            LightSource.getAPI().log(player, "Enter item id " + ChatColor.YELLOW + "(Example: MyBestItem or MYBESTITEMTOO) ");
            player.closeInventory();
        }
    }

}