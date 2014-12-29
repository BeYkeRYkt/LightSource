package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.gui.Icon;
import ykt.BeYkeRYkt.LightSource.api.gui.Menu;
import ykt.BeYkeRYkt.LightSource.gui.WorldBlockTransform;

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
        Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("worldsMenu");
        LightSource.getAPI().getGUIManager().openMenu(player, menu);
    }

}