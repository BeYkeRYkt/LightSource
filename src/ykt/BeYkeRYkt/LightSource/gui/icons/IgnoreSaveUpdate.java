package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.gui.Icon;
import ykt.BeYkeRYkt.LightSource.api.gui.Menu;

public class IgnoreSaveUpdate extends Icon {

    public IgnoreSaveUpdate() {
        super("ignoreSaveUpdate", Material.REDSTONE);

        setName(ChatColor.GOLD + "Ignore save update light");
        getLore().add(ChatColor.WHITE + "Ignoring the checking location");
        getLore().add(ChatColor.WHITE + "for the update.");
        getLore().add(ChatColor.GREEN + "If the setting is off:");
        getLore().add(ChatColor.GREEN + "Chunk will be updated if the source moves.");
        getLore().add(ChatColor.RED + "If the setting is enabled:");
        getLore().add(ChatColor.RED + "Chunk will be constantly updated.");
        getLore().add(ChatColor.GOLD + "Status: ");
        getLore().add(String.valueOf(LightSource.getInstance().getDB().isIgnoreSaveUpdate()));
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        if (!LightSource.getInstance().getDB().isIgnoreSaveUpdate()) {
            LightSource.getInstance().getDB().setIgnoreSaveUpdate(true);
        } else {
            LightSource.getInstance().getDB().setIgnoreSaveUpdate(false);
        }
        getLore().set(7, String.valueOf(LightSource.getInstance().getDB().isIgnoreSaveUpdate()));

        Menu menu1 = LightSource.getAPI().getGUIManager().getMenuFromId("optionsMenu");
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
        LightSource.getAPI().getGUIManager().openMenu(player, menu1);
    }
}