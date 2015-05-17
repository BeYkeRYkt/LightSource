package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightAPI.LightAPI;
import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;

public class LC_LightLevel extends Icon {

    private int level;

    public LC_LightLevel(int level) {
        super("lc_lightlevel_" + level, Material.GLOWSTONE);
        this.level = level;
        setName("" + level);
        getLore().add("");
        getLore().add(ChatColor.WHITE + "Create a level of illumination: " + level);
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.closeInventory();

        LightAPI.createLight(player.getLocation(), level, true);
        player.getLocation().getChunk().unload(true);
        player.getLocation().getChunk().load(true);
        LightSource.getInstance().log(player, ChatColor.GREEN + "Light successfully created!");
    }

}