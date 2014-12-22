package ykt.BeYkeRYkt.LightSource.gui.icons;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;

public class GoToPage extends Icon {

    private int page;

    public GoToPage(int page) {
        super("goto_page_" + page, new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getWoolData()));
        this.page = page;
        setName("Go to " + ChatColor.YELLOW + (page + 1));
    }

    @Override
    public void onItemClick(InventoryClickEvent event) {
        Menu menu = LightSource.getAPI().getGUIManager().getMenuFromId("page_" + page);
        LightSource.getAPI().getGUIManager().openMenu((Player) event.getWhoClicked(), menu);
    }

}