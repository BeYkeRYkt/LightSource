package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.gui.Icon;
import ykt.BeYkeRYkt.LightSource.api.gui.Menu;

public class Options extends Icon {

    public Options() {
        super("options", Material.BOOKSHELF);
        setName(ChatColor.GREEN + "Options");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.ANVIL_USE, 1, 1);

        Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("optionsMenu");
        LightSource.getAPI().getGUIManager().openMenu(player, menu);
    }
}