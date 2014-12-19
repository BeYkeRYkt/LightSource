package ykt.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class MainMenu extends Menu {

    public MainMenu() {
        super("mainMenu", LightSource.getInstance().getName(), 18);

        Icon playerLight = LightSource.getAPI().getGUIManager().getIconFromId("playerLight");
        addItem(playerLight, 1);

        Icon entityLight = LightSource.getAPI().getGUIManager().getIconFromId("entityLight");
        addItem(entityLight, 2);

        Icon itemLight = LightSource.getAPI().getGUIManager().getIconFromId("itemLight");
        addItem(itemLight, 3);

        Icon lightDamage = LightSource.getAPI().getGUIManager().getIconFromId("lightsourcedamage");
        addItem(lightDamage, 4);

        Icon items = LightSource.getAPI().getGUIManager().getIconFromId("items");
        addItem(items, 5);

        Icon worlds = LightSource.getAPI().getGUIManager().getIconFromId("worlds");
        addItem(worlds, 6);

        Icon about = LightSource.getAPI().getGUIManager().getIconFromId("about");
        addItem(about, 9);
    }

    @Override
    public void onOpenMenu(InventoryOpenEvent event) {
    }

    @Override
    public void onCloseMenu(InventoryCloseEvent event) {
    }
}