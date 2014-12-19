package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.gui.Icon;

public class Items extends Icon {

    public Items() {
        super("items", Material.CHEST);
        setName(ChatColor.GREEN + "Items");

        getLore().add(ChatColor.WHITE + "Comming soon...");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        // Item Editor 3000
        // Edit: Create, Delete light source...
        // End.
        Player player = (Player) event.getWhoClicked();
        // LightSource.getAPI().getGUIManager().openMenu(player,
        // LightSource.getAPI().getGUIManager().getMenuFromId("itemsMenu"));
        player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
    }
}