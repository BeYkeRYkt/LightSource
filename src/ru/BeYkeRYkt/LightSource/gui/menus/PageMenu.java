package ru.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;
import ru.BeYkeRYkt.LightSource.items.LightItem;

public class PageMenu extends Menu {

	public PageMenu(int page) {
		super("page_" + page, "Page " + (page + 1), 54);

		for (int i = 0; i < 45; i++) {
			if (!LightSource.getInstance().getEditorManager().getCachedItemsList().isEmpty()) {
				LightItem item = LightSource.getInstance().getEditorManager().getCachedItemsList().get(0);
				Icon icon = LightSource.getInstance().getGUIManager().getIconFromId("item_" + item.getId());
				addItem(icon, i + 1);
				LightSource.getInstance().getEditorManager().getCachedItemsList().remove(0);
			}
		}

		Icon create = LightSource.getInstance().getGUIManager().getIconFromId("createItem");
		addItem(create, 46);

		if (LightSource.getInstance().getGUIManager().getIconFromId("goto_page_" + (page - 1)) != null) {
			Icon previous = LightSource.getInstance().getGUIManager().getIconFromId("goto_page_" + (page - 1));
			addItem(previous, 52);
		}

		if (LightSource.getInstance().getGUIManager().getIconFromId("goto_page_" + (page + 1)) != null) {
			Icon next = LightSource.getInstance().getGUIManager().getIconFromId("goto_page_" + (page + 1));
			addItem(next, 53);
		}

		Icon back = LightSource.getInstance().getGUIManager().getIconFromId("back");
		addItem(back, 54);
	}

	@Override
	public void onOpenMenu(InventoryOpenEvent event) {
	}

	@Override
	public void onCloseMenu(InventoryCloseEvent event) {
	}
}
