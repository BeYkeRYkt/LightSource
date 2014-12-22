package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class Back_Pages extends Icon {

    public Back_Pages() {
        super("back_pages", Material.WORKBENCH);
        setName(ChatColor.RED + "Back");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("page_0");
        Player player = (Player) event.getWhoClicked();
        LightSource.getAPI().getGUIManager().openMenu(player, menu);

        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
    }
}