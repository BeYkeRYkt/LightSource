package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;

public class Items extends Icon {

    public Items() {
        super("items", Material.CHEST);
        setName(ChatColor.GREEN + "Items");

        getLore().add(ChatColor.WHITE + "Online item editor!");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);

        LightSource.getAPI().getGUIManager().openMenu(player, LightSource.getAPI().getGUIManager().getMenuFromId("page_0"));
    }
}