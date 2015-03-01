package ru.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;

public class Back extends Icon {

    public Back() {
        super("back", Material.WORKBENCH);

        setName(ChatColor.GREEN + "Back");
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Menu menu1 = LightSource.getInstance().getGUIManager().getMenuFromId("mainMenu");
        Player player = (Player) event.getWhoClicked();
        LightSource.getInstance().getGUIManager().openMenu(player, menu1);
    }
}