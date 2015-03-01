package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;
import ru.BeYkeRYkt.LightSource.gui.WorldBlockTransform;

public class World_Name extends Icon {

    private String worldname;

    public World_Name(WorldBlockTransform wbt) {
        super("world_" + wbt.getWorld().getName(), wbt.getBlock());
        this.worldname = wbt.getWorld().getName();
        setName(worldname);
        getLore().add("");
        getLore().add(ChatColor.GOLD + "Status: ");
        getLore().add(String.valueOf(LightSource.getInstance().getDB().getWorld(worldname)));
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        if (!LightSource.getInstance().getDB().getWorld(worldname)) {
            LightSource.getInstance().getDB().setWorld(worldname, true);
        } else {
            LightSource.getInstance().getDB().setWorld(worldname, false);
        }
        // replace
        getLore().set(2, String.valueOf(LightSource.getInstance().getDB().getWorld(worldname)));

        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
        Menu menu = LightSource.getInstance().getGUIManager().getMenuFromId("worldsMenu");
        LightSource.getInstance().getGUIManager().openMenu(player, menu);
    }

}