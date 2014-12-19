package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class Worlds extends Icon {

    public Worlds() {
        super("worlds", Material.GRASS);
        setName(ChatColor.GREEN + "Worlds");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("worldsMenu");
        LightSource.getAPI().getGUIManager().openMenu(player, menu);
    }
}