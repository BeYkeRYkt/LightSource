package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;

public class PlayerLight extends Icon {

    public PlayerLight() {
        super("playerLight", new ItemStack(Material.SKULL_ITEM, 1, (short) 3));
        setName(ChatColor.GREEN + "PlayerLight");
        getLore().add("");
        getLore().add(ChatColor.DARK_RED + "WARNING!");
        getLore().add(ChatColor.RED + "This option can cause colossal lags!");
        getLore().add(ChatColor.GOLD + "Status: ");
        getLore().add(String.valueOf(LightSource.getInstance().getDB().isPlayerLight()));
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        if (!LightSource.getInstance().getDB().isPlayerLight()) {
            LightSource.getInstance().getDB().setPlayerLight(true);
        } else {
            LightSource.getInstance().getDB().setPlayerLight(false);
        }
        // replace
        getLore().set(4, String.valueOf(LightSource.getInstance().getDB().isPlayerLight()));

        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("optionsMenu");
        LightSource.getAPI().getGUIManager().openMenu(player, menu);
    }
}