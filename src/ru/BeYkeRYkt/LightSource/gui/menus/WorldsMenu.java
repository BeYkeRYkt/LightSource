package ru.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;

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
    }

    @Override
    public void onCloseMenu(InventoryCloseEvent event) {
    }
}