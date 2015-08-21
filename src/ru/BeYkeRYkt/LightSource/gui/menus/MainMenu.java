package ru.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;

public class MainMenu extends Menu {

	public MainMenu() {
		super("mainMenu", LightSource.getInstance().getName(), 18);

		Icon items = LightSource.getInstance().getGUIManager().getIconFromId("items");
		addItem(items, 1);

		Icon worlds = LightSource.getInstance().getGUIManager().getIconFromId("worlds");
		addItem(worlds, 2);

		Icon update = LightSource.getInstance().getGUIManager().getIconFromId("checkUpdate");
		addItem(update, 3);

		Icon options = LightSource.getInstance().getGUIManager().getIconFromId("options");
		addItem(options, 4);

		Icon about = LightSource.getInstance().getGUIManager().getIconFromId("about");
		addItem(about, 18);
	}

	@Override
	public void onOpenMenu(InventoryOpenEvent event) {
	}

	@Override
	public void onCloseMenu(InventoryCloseEvent event) {
	}
}
