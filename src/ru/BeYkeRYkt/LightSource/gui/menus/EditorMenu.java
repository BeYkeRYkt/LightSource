package ru.BeYkeRYkt.LightSource.gui.menus;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import ru.BeYkeRYkt.LightSource.LightAPI;
import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;
import ru.BeYkeRYkt.LightSource.gui.editor.PlayerEditor;

public class EditorMenu extends Menu {

    public EditorMenu() {
        super("editorMenu", "Item Editor", 18);
        Icon name = LightSource.getAPI().getGUIManager().getIconFromId("editorName");
        addItem(name, 1);

        Icon data = LightSource.getAPI().getGUIManager().getIconFromId("editorData");
        addItem(data, 2);

        Icon burn = LightSource.getAPI().getGUIManager().getIconFromId("editorBurn");
        addItem(burn, 3);

        Icon level = LightSource.getAPI().getGUIManager().getIconFromId("editorLevel");
        addItem(level, 4);

        Icon delete = LightSource.getAPI().getGUIManager().getIconFromId("deleteItem");
        addItem(delete, 5);

        Icon back = LightSource.getAPI().getGUIManager().getIconFromId("back_pages");
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
        if (LightAPI.getEditorManager().isEditor(player.getName())) {
            PlayerEditor editor = LightAPI.getEditorManager().getEditor(player.getName());
            if (editor.getStage() == 0) {
                LightAPI.getEditorManager().removeEditor(editor);
            }
        }
    }
}