package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;
import ru.BeYkeRYkt.LightSource.gui.editor.PlayerEditor;

public class ChangeName extends Icon {

    public ChangeName() {
        super("editorName", Material.NAME_TAG);
        setName(ChatColor.AQUA + "Change name");
        getLore().add("");
        getLore().add(ChatColor.WHITE + "Current name: ");
        getLore().add(ChatColor.GOLD + "Click item for edit...");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        LightSource.getInstance().log(player, "Enter new name (Chat)");

        PlayerEditor editor = LightSource.getInstance().getEditorManager().getEditor(player.getName());
        editor.setStage(1);
        player.closeInventory();
    }

    @Override
    public void onMenuOpen(Menu menu, Player player) {
        PlayerEditor editor = LightSource.getInstance().getEditorManager().getEditor(player.getName());
        getLore().set(1, ChatColor.WHITE + "Current name: " + ChatColor.GREEN + editor.getItem().getName());
    }

}