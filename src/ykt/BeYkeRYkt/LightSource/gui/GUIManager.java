package ykt.BeYkeRYkt.LightSource.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.gui.icons.About;
import ykt.BeYkeRYkt.LightSource.gui.icons.Back;
import ykt.BeYkeRYkt.LightSource.gui.icons.EntityLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.IgnoreSaveUpdate;
import ykt.BeYkeRYkt.LightSource.gui.icons.ItemLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.Items;
import ykt.BeYkeRYkt.LightSource.gui.icons.LightSourceDamage;
import ykt.BeYkeRYkt.LightSource.gui.icons.PlayerLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.World_Name;
import ykt.BeYkeRYkt.LightSource.gui.icons.Worlds;
import ykt.BeYkeRYkt.LightSource.gui.menus.MainMenu;
import ykt.BeYkeRYkt.LightSource.gui.menus.WorldsMenu;

/**
 * 
 * Port from GlowLib
 * 
 * @author GlowSparkletBox
 *
 */
public class GUIManager {

    private List<Menu> menus = new ArrayList<Menu>();
    private List<Icon> icons = new ArrayList<Icon>();

    public void load() {
        // Icons
        // getGUIManager().registerIcon(new NullLOL());
        LightSource.getAPI().getGUIManager().registerIcon(new About());
        LightSource.getAPI().getGUIManager().registerIcon(new Back());
        LightSource.getAPI().getGUIManager().registerIcon(new EntityLight());
        LightSource.getAPI().getGUIManager().registerIcon(new ItemLight());
        LightSource.getAPI().getGUIManager().registerIcon(new Items());
        LightSource.getAPI().getGUIManager().registerIcon(new PlayerLight());
        LightSource.getAPI().getGUIManager().registerIcon(new Worlds());
        LightSource.getAPI().getGUIManager().registerIcon(new LightSourceDamage());
        LightSource.getAPI().getGUIManager().registerIcon(new IgnoreSaveUpdate());

        // init worlds
        // also: MONSTERKILL!
        for (World world : Bukkit.getWorlds()) {
            WorldBlockTransform wbt = new WorldBlockTransform(world);
            LightSource.getAPI().getGUIManager().registerIcon(new World_Name(wbt));
        }

        // Menus
        LightSource.getAPI().getGUIManager().registerMenu(new MainMenu());
        LightSource.getAPI().getGUIManager().registerMenu(new WorldsMenu());
    }

    public Inventory openMenu(Player player, Menu menu) {
        Inventory inv = Bukkit.createInventory(null, menu.getSlots(), menu.getName());
        // init icons
        if (!menu.getIcons().isEmpty()) {
            for (Icon icon : menu.getIcons().keySet()) {
                int num = menu.getIcons().get(icon);
                ItemStack item = icon.getItemStack();
                inv.setItem(num, item);
            }
        }
        player.openInventory(inv);
        return inv;
    }

    public void registerMenu(Menu menu) {
        if (menu == null)
            return;
        if (!isMenu(menu.getName())) {
            menus.add(menu);
        }
    }

    public void registerIcon(Icon icon) {
        if (icon == null)
            return;
        if (!isIcon(icon.getItemStack())) {
            icons.add(icon);
        }
    }

    @Deprecated
    public void unregisterMenu(Menu menu) {
    }

    @Deprecated
    public void unregisterIcon(Icon icon) {
    }

    public Menu getMenuFromId(String id) {
        for (Menu menu : menus) {
            if (menu.getId().equals(id)) {
                return menu;
            }
        }
        return null;
    }

    public Menu getMenuFromName(String name) {
        for (Menu menu : menus) {
            if (menu.getName().equals(name)) {
                return menu;
            }
        }
        return null;
    }

    public Icon getIconFromId(String id) {
        for (Icon icon : icons) {
            if (icon.getId().equals(id)) {
                return icon;
            }
        }
        return null;
    }

    public Icon getIconFromName(String name) {
        for (Icon icon : icons) {
            if (icon.getName().equals(name)) {
                return icon;
            }
        }
        return null;
    }

    public boolean isMenu(String nameInv) {
        if (nameInv == null)
            return false;
        for (Menu menu : menus) {
            if (menu.getName().equals(nameInv)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIcon(ItemStack item) {
        if (item == null)
            return false;
        for (Icon icon : icons) {
            if (icon.getMaterial() == item.getType()) {
                if (!item.getItemMeta().hasDisplayName())
                    return false;
                if (icon.getName().equals(item.getItemMeta().getDisplayName())) {
                    return true;
                }
            }
        }
        return false;
    }
}