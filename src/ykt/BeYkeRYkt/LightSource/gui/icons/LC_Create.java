package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.gui.Icon;
import ykt.BeYkeRYkt.LightSource.api.gui.Menu;

public class LC_Create extends Icon {

    public LC_Create() {
        super("lc_create", Material.FLINT_AND_STEEL);
        setName(ChatColor.YELLOW + "Create light");
        getLore().add("");
        getLore().add(ChatColor.WHITE + "Put an invisible light source on your position");
        // needed
        getLore().add("");
        getLore().add("");
        getLore().add("");
        getLore().add("");
        getLore().add("");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("lc_createMenu");
        LightSource.getAPI().getGUIManager().openMenu(player, menu);
    }

    @Override
    public void onMenuOpen(Menu menu, Player player) {
        getLore().set(2, ChatColor.GOLD + "Your Location:");
        getLore().set(3, ChatColor.WHITE + "X= " + player.getLocation().getBlockX());
        getLore().set(4, ChatColor.WHITE + "Y= " + player.getLocation().getBlockY());
        getLore().set(5, ChatColor.WHITE + "Z= " + player.getLocation().getBlockZ());
        getLore().set(6, ChatColor.GREEN + "LightLevel: " + player.getLocation().getBlock().getLightLevel());
    }
}