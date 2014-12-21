package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.sources.ChunkCoords;

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

        LightAPI.createLight(player.getLocation(), level);
        LightAPI.updateChunk(new ChunkCoords(player.getLocation().getChunk()));
        player.getLocation().getChunk().unload(true);
        player.getLocation().getChunk().load(true);
        LightSource.getAPI().log(player, ChatColor.GREEN + "Light successfully created!");
    }

}