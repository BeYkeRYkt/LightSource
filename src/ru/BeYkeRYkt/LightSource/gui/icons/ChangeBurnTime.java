package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightAPI;
import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;
import ru.BeYkeRYkt.LightSource.gui.editor.PlayerEditor;

public class ChangeBurnTime extends Icon {

    public ChangeBurnTime() {
        super("editorBurn", Material.TORCH);
        setName(ChatColor.AQUA + "Change burn time");
        getLore().add("");
        getLore().add(ChatColor.WHITE + "Current time: ");
        getLore().add(ChatColor.GOLD + "Click item for edit...");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        LightSource.getAPI().log(player, "Enter new time in seconds (Chat)");

        PlayerEditor editor = LightAPI.getEditorManager().getEditor(player.getName());
        editor.setStage(2);
        player.closeInventory();
    }

    @Override
    public void onMenuOpen(Menu menu, Player player) {
        PlayerEditor editor = LightAPI.getEditorManager().getEditor(player.getName());
        getLore().set(1, ChatColor.WHITE + "Current time: " + ChatColor.GREEN + editor.getItem().getMaxBurnTime() + ChatColor.WHITE + " seconds");
    }

}