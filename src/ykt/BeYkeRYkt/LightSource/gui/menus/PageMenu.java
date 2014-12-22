package ykt.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;
import ykt.BeYkeRYkt.LightSource.items.LightItem;

public class PageMenu extends Menu {

    public PageMenu(int page) {
        super("page_" + page, "Page " + (page + 1), 54);

        for (int i = 0; i < 45; i++) {
            if (!LightAPI.getEditorManager().getCachedItemsList().isEmpty()) {
                LightItem item = LightAPI.getEditorManager().getCachedItemsList().get(0);
                Icon icon = LightSource.getAPI().getGUIManager().getIconFromId("item_" + item.getId());
                addItem(icon, i + 1);
                LightAPI.getEditorManager().getCachedItemsList().remove(0);
            }
        }

        if (LightSource.getAPI().getGUIManager().getIconFromId("goto_page_" + (page - 1)) != null) {
            Icon previous = LightSource.getAPI().getGUIManager().getIconFromId("goto_page_" + (page - 1));
            addItem(previous, 52);
        }

        if (LightSource.getAPI().getGUIManager().getIconFromId("goto_page_" + (page + 1)) != null) {
            Icon next = LightSource.getAPI().getGUIManager().getIconFromId("goto_page_" + (page + 1));
            addItem(next, 53);
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