package ru.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;

public class LightCreatorMenu extends Menu {

    public LightCreatorMenu() {
        super("lc_mainMenu", "Light Static Creator", 18);

        Icon create = LightSource.getAPI().getGUIManager().getIconFromId("lc_create");
        addItem(create, 1);

        Icon delete = LightSource.getAPI().getGUIManager().getIconFromId("lc_delete");
        addItem(delete, 2);
    }

    @Override
    public void onOpenMenu(InventoryOpenEvent event) {
    }

    @Override
    public void onCloseMenu(InventoryCloseEvent event) {
    }
}