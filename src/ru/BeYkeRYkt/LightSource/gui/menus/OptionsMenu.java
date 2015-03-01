package ru.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;

public class OptionsMenu extends Menu {

    public OptionsMenu() {
        super("optionsMenu", "Options", 18);

        Icon playerLight = LightSource.getInstance().getGUIManager().getIconFromId("playerLight");
        addItem(playerLight, 1);

        Icon entityLight = LightSource.getInstance().getGUIManager().getIconFromId("entityLight");
        addItem(entityLight, 2);

        Icon itemLight = LightSource.getInstance().getGUIManager().getIconFromId("itemLight");
        addItem(itemLight, 3);

        Icon burn = LightSource.getInstance().getGUIManager().getIconFromId("burnLight");
        addItem(burn, 4);

        Icon ignore = LightSource.getInstance().getGUIManager().getIconFromId("ignoreSaveUpdate");
        addItem(ignore, 5);

        Icon lightDamage = LightSource.getInstance().getGUIManager().getIconFromId("lightsourcedamage");
        addItem(lightDamage, 6);

        Icon back = LightSource.getInstance().getGUIManager().getIconFromId("back");
        addItem(back, 18);
    }

    @Override
    public void onOpenMenu(InventoryOpenEvent event) {
    }

    @Override
    public void onCloseMenu(InventoryCloseEvent event) {
    }
}