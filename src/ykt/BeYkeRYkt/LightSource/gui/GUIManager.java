package ykt.BeYkeRYkt.LightSource.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.gui.icons.About;
import ykt.BeYkeRYkt.LightSource.gui.icons.Back;
import ykt.BeYkeRYkt.LightSource.gui.icons.Back_Pages;
import ykt.BeYkeRYkt.LightSource.gui.icons.ChangeBurnTime;
import ykt.BeYkeRYkt.LightSource.gui.icons.ChangeLightLevel;
import ykt.BeYkeRYkt.LightSource.gui.icons.ChangeName;
import ykt.BeYkeRYkt.LightSource.gui.icons.CheckUpdate;
import ykt.BeYkeRYkt.LightSource.gui.icons.CreateItem;
import ykt.BeYkeRYkt.LightSource.gui.icons.DeleteItem;
import ykt.BeYkeRYkt.LightSource.gui.icons.EntityLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.GoToPage;
import ykt.BeYkeRYkt.LightSource.gui.icons.IgnoreSaveUpdate;
import ykt.BeYkeRYkt.LightSource.gui.icons.ItemIcon;
import ykt.BeYkeRYkt.LightSource.gui.icons.ItemLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.Items;
import ykt.BeYkeRYkt.LightSource.gui.icons.LC_Create;
import ykt.BeYkeRYkt.LightSource.gui.icons.LC_Delete;
import ykt.BeYkeRYkt.LightSource.gui.icons.LC_LightLevel;
import ykt.BeYkeRYkt.LightSource.gui.icons.LightSourceDamage;
import ykt.BeYkeRYkt.LightSource.gui.icons.Options;
import ykt.BeYkeRYkt.LightSource.gui.icons.PlayerLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.World_Name;
import ykt.BeYkeRYkt.LightSource.gui.icons.Worlds;
import ykt.BeYkeRYkt.LightSource.gui.menus.EditorMenu;
import ykt.BeYkeRYkt.LightSource.gui.menus.LightCreatorCreate;
import ykt.BeYkeRYkt.LightSource.gui.menus.LightCreatorMenu;
import ykt.BeYkeRYkt.LightSource.gui.menus.MainMenu;
import ykt.BeYkeRYkt.LightSource.gui.menus.OptionsMenu;
import ykt.BeYkeRYkt.LightSource.gui.menus.PageMenu;
import ykt.BeYkeRYkt.LightSource.gui.menus.WorldsMenu;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;

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
        registerIcon(new About());
        registerIcon(new Back());
        registerIcon(new EntityLight());
        registerIcon(new ItemLight());
        registerIcon(new Items());
        registerIcon(new PlayerLight());
        registerIcon(new Worlds());
        registerIcon(new LightSourceDamage());
        registerIcon(new IgnoreSaveUpdate());
        registerIcon(new LC_Create());
        registerIcon(new LC_Delete());
        registerIcon(new ChangeName());
        registerIcon(new ChangeBurnTime());
        registerIcon(new ChangeLightLevel());
        registerIcon(new Back_Pages());
        registerIcon(new CreateItem());
        registerIcon(new DeleteItem());
        registerIcon(new Options());
        registerIcon(new CheckUpdate());

        // init worlds
        // also: MONSTERKILL!
        for (World world : Bukkit.getWorlds()) {
            WorldBlockTransform wbt = new WorldBlockTransform(world);
            registerIcon(new World_Name(wbt));
        }

        // init lightlevels :D
        for (int i = 0; i < 15; i++) {
            LC_LightLevel light = new LC_LightLevel(i + 1);
            registerIcon(light);
        }

        // 2.0.3: init more lightitems :D
        for (int i = 0; i < ItemManager.getList().size(); i++) {
            LightItem item = ItemManager.getList().get(i);
            ItemIcon icon = new ItemIcon(item);
            registerIcon(icon);
        }

        // 2.0.3: init large pages :D
        for (int i = 0; i < LightAPI.getEditorManager().getPages(); i++) {
            registerIcon(new GoToPage(i));
        }

        // Menus
        registerMenu(new MainMenu());
        registerMenu(new WorldsMenu());
        registerMenu(new LightCreatorMenu());
        registerMenu(new LightCreatorCreate());
        registerMenu(new EditorMenu());
        registerMenu(new OptionsMenu());

        // 2.0.3: init large pages :D
        for (int i = 0; i < LightAPI.getEditorManager().getPages(); i++) {
            registerMenu(new PageMenu(i));
        }
    }

    public void refresh() {
        icons.clear();
        menus.clear();

        LightAPI.getEditorManager().refreshCachedItemsList();
        load();
    }

    public Inventory openMenu(Player player, Menu menu) {
        Inventory inv = Bukkit.createInventory(null, menu.getSlots(), menu.getName());
        // init icons
        if (!menu.getIcons().isEmpty()) {
            for (Icon icon : menu.getIcons().keySet()) {
                int num = menu.getIcons().get(icon);
                icon.onMenuOpen(menu, player);
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