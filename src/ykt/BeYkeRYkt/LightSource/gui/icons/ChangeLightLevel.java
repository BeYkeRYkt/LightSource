package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.editor.PlayerEditor;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class ChangeLightLevel extends Icon {

    public ChangeLightLevel() {
        super("editorLevel", Material.GLOWSTONE_DUST);
        setName(ChatColor.AQUA + "Change light level");
        getLore().add("");
        getLore().add(ChatColor.WHITE + "Current level: ");
        getLore().add(ChatColor.GOLD + "Click item for edit...");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        LightSource.getAPI().log(player, "Enter new level 1 - 15 (Chat)");

        PlayerEditor editor = LightAPI.getEditorManager().getEditor(player.getName());
        editor.setStage(3);
        player.closeInventory();
    }

    @Override
    public void onMenuOpen(Menu menu, Player player) {
        PlayerEditor editor = LightAPI.getEditorManager().getEditor(player.getName());
        getLore().set(1, ChatColor.WHITE + "Current level: " + ChatColor.GREEN + editor.getItem().getMaxLevelLight() + ChatColor.WHITE + " / 15");
    }

}