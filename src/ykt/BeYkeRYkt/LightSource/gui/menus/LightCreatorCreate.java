package ykt.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.gui.Icon;
import ykt.BeYkeRYkt.LightSource.api.gui.Menu;

public class LightCreatorCreate extends Menu {

    public LightCreatorCreate() {
        super("lc_createMenu", "Create light", 18);

        for (int i = 0; i < 15; i++) {
            Icon lightIcon = LightSource.getAPI().getGUIManager().getIconFromId("lc_lightlevel_" + (i + 1));
            addItem(lightIcon, i + 1);
        }
    }

    @Override
    public void onOpenMenu(InventoryOpenEvent event) {
    }

    @Override
    public void onCloseMenu(InventoryCloseEvent event) {
    }
}