package ykt.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class WorldsMenu extends Menu {

    public WorldsMenu() {
        super("worldsMenu", ChatColor.GREEN + "Worlds", 54);

        for (int i = 0; i < Bukkit.getWorlds().size(); i++) {
            World world = Bukkit.getWorlds().get(i);
            Icon world_icon = LightSource.getAPI().getGUIManager().getIconFromId("world_" + world.getName());
            addItem(world_icon, i + 1);
        }

        Icon back = LightSource.getAPI().getGUIManager().getIconFromId("back");
        addItem(back, 54);
    }

    @Override
    public void onOpenMenu(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        player.playSound(player.getLocation(), Sound.SHEEP_IDLE, 1, 1);
    }

    @Override
    public void onCloseMenu(InventoryCloseEvent event) {
    }
}