package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.editor.PlayerEditor;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

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
    }

    @Override
    public void onMenuOpen(Menu menu, Player player) {
        PlayerEditor editor = LightAPI.getEditorManager().getEditor(player.getName());
        getLore().set(1, ChatColor.WHITE + "Current time: " + ChatColor.GREEN + editor.getItem().getBurnTime() + ChatColor.WHITE + " seconds");
    }

}