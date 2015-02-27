package ru.BeYkeRYkt.LightSource.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ru.BeYkeRYkt.LightSource.LightAPI;
import ru.BeYkeRYkt.LightSource.events.PlayerOpenMenuEvent;
import ru.BeYkeRYkt.LightSource.gui.icons.About;
import ru.BeYkeRYkt.LightSource.gui.icons.Back;
import ru.BeYkeRYkt.LightSource.gui.icons.Back_Pages;
import ru.BeYkeRYkt.LightSource.gui.icons.BurnLight;
import ru.BeYkeRYkt.LightSource.gui.icons.ChangeBurnTime;
import ru.BeYkeRYkt.LightSource.gui.icons.ChangeLightLevel;
import ru.BeYkeRYkt.LightSource.gui.icons.ChangeName;
import ru.BeYkeRYkt.LightSource.gui.icons.CheckUpdate;
import ru.BeYkeRYkt.LightSource.gui.icons.CreateItem;
import ru.BeYkeRYkt.LightSource.gui.icons.DeleteItem;
import ru.BeYkeRYkt.LightSource.gui.icons.EntityLight;
import ru.BeYkeRYkt.LightSource.gui.icons.GoToPage;
import ru.BeYkeRYkt.LightSource.gui.icons.IgnoreSaveUpdate;
import ru.BeYkeRYkt.LightSource.gui.icons.ItemIcon;
import ru.BeYkeRYkt.LightSource.gui.icons.ItemLight;
import ru.BeYkeRYkt.LightSource.gui.icons.Items;
import ru.BeYkeRYkt.LightSource.gui.icons.LC_Create;
import ru.BeYkeRYkt.LightSource.gui.icons.LC_Delete;
import ru.BeYkeRYkt.LightSource.gui.icons.LC_LightLevel;
import ru.BeYkeRYkt.LightSource.gui.icons.LightSourceDamage;
import ru.BeYkeRYkt.LightSource.gui.icons.Options;
import ru.BeYkeRYkt.LightSource.gui.icons.PlayerLight;
import ru.BeYkeRYkt.LightSource.gui.icons.SetData;
import ru.BeYkeRYkt.LightSource.gui.icons.World_Name;
import ru.BeYkeRYkt.LightSource.gui.icons.Worlds;
import ru.BeYkeRYkt.LightSource.gui.menus.EditorMenu;
import ru.BeYkeRYkt.LightSource.gui.menus.LightCreatorCreate;
import ru.BeYkeRYkt.LightSource.gui.menus.LightCreatorMenu;
import ru.BeYkeRYkt.LightSource.gui.menus.MainMenu;
import ru.BeYkeRYkt.LightSource.gui.menus.OptionsMenu;
import ru.BeYkeRYkt.LightSource.gui.menus.PageMenu;
import ru.BeYkeRYkt.LightSource.gui.menus.WorldsMenu;
import ru.BeYkeRYkt.LightSource.items.ItemManager;
import ru.BeYkeRYkt.LightSource.items.LightItem;

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
        registerIcon(new BurnLight());
        registerIcon(new SetData());

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
        PlayerOpenMenuEvent event = new PlayerOpenMenuEvent(player, menu);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled())
            return Bukkit.createInventory(null, menu.getSlots(), menu.getName());

        Inventory inv = Bukkit.createInventory(null, event.getMenu().getSlots(), event.getMenu().getName());
        // init icons
        if (!event.getMenu().getIcons().isEmpty()) {
            for (Icon icon : event.getMenu().getIcons().keySet()) {
                int num = event.getMenu().getIcons().get(icon);
                icon.onMenuOpen(event.getMenu(), player);
                ItemStack item = icon.getItemStack();
                inv.setItem(num, item);
            }
        }
        event.getPlayer().openInventory(inv);
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