package ru.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;
import ru.BeYkeRYkt.LightSource.gui.editor.PlayerEditor;

public class EditorMenu extends Menu {

    public EditorMenu() {
        super("editorMenu", "Item Editor", 18);
        Icon name = LightSource.getInstance().getGUIManager().getIconFromId("editorName");
        addItem(name, 1);

        Icon data = LightSource.getInstance().getGUIManager().getIconFromId("editorData");
        addItem(data, 2);

        Icon level = LightSource.getInstance().getGUIManager().getIconFromId("editorLevel");
        addItem(level, 3);

        Icon delete = LightSource.getInstance().getGUIManager().getIconFromId("deleteItem");
        addItem(delete, 4);

        Icon back = LightSource.getInstance().getGUIManager().getIconFromId("back_pages");
        addItem(back, 18);
    }

    @Override
    public void onOpenMenu(InventoryOpenEvent event) {
        Player player = (Player) event.getPlayer();
        player.playSound(player.getLocation(), Sound.DOOR_OPEN, 1, 1);
    }

    @Override
    public void onCloseMenu(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (LightSource.getInstance().getEditorManager().isEditor(player.getName())) {
            PlayerEditor editor = LightSource.getInstance().getEditorManager().getEditor(player.getName());
            if (editor.getStage() == 0) {
                LightSource.getInstance().getEditorManager().removeEditor(editor);
            }
        }
    }
}