package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;

public class LightSourceDamage extends Icon {

    public LightSourceDamage() {
        super("lightsourcedamage", Material.LAVA_BUCKET);

        setName(ChatColor.RED + "Fire Damage");
        getLore().add(ChatColor.WHITE + "Enabling and disabling burning");
        getLore().add(ChatColor.WHITE + "enemy during battle.");
        getLore().add(ChatColor.GOLD + "Status: ");
        getLore().add(String.valueOf(LightSource.getInstance().getDB().isLightSourceDamage()));
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        if (!LightSource.getInstance().getDB().isLightSourceDamage()) {
            LightSource.getInstance().getDB().setLightSourceDamage(true);
        } else {
            LightSource.getInstance().getDB().setLightSourceDamage(false);
        }
        // replace
        getLore().set(3, String.valueOf(LightSource.getInstance().getDB().isLightSourceDamage()));

        Menu menu1 = LightSource.getAPI().getGUIManager().getMenuFromId("optionsMenu");
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 1, 1);
        LightSource.getAPI().getGUIManager().openMenu(player, menu1);
    }
}