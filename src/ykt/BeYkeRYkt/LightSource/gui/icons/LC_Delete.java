package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class LC_Delete extends Icon {

    public LC_Delete() {
        super("lc_delete", Material.WATER_BUCKET);
        setName(ChatColor.YELLOW + "Delete light");
        getLore().add("");
        getLore().add(ChatColor.WHITE + "Delete an invisible light source on your position");
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
        player.closeInventory();

        LightAPI.deleteLight(player.getLocation(), true);
        LightSource.getAPI().log(player, ChatColor.GREEN + "Light successfully deleted!");
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