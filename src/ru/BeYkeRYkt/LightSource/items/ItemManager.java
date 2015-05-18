package ru.BeYkeRYkt.LightSource.items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.sources.Source;

public class ItemManager {

    private static FileConfiguration customConfig = null;
    private File customFile = null;
    private static ArrayList<LightItem> list = new ArrayList<LightItem>();
    private static LightSource plugin = LightSource.getInstance();
    private String name = "Items";

    public void loadItems() {
        for (String str : getConfig().getKeys(false)) {
            String name = getConfig().getString(str + ".name");

            if (name != null) {
                name = ChatColor.translateAlternateColorCodes('&', name);
            }

            Material material = Material.getMaterial(getConfig().getString(str + ".material"));
            int data = getConfig().getInt(str + ".data");
            int level = getConfig().getInt(str + ".lightlevel");

            LightItem item = new LightItem(str, name, material, data, level);

            addLightSource(item, str);
        }
    }

    public void reloadConfig() {
        if (this.customFile == null) {
            String folder = LightSource.getInstance().getDataFolder() + "";
            this.customFile = new File(folder, name + ".yml");
        }
        ItemManager.customConfig = YamlConfiguration.loadConfiguration(this.customFile);

        // Look for defaults in the jar
        // InputStream defConfigStream =
        // LightSource.getInstance().getResource(name + ".yml");
        // if (defConfigStream != null) {
        // YamlConfiguration defConfig =
        // YamlConfiguration.loadConfiguration(defConfigStream);
        // this.customConfig.setDefaults(defConfig);
        // }
    }

    public FileConfiguration getConfig() {
        if (ItemManager.customConfig == null) {
            reloadConfig();
        }
        return ItemManager.customConfig;
    }

    public void saveConfig() {
        if (ItemManager.customConfig == null || this.customFile == null) {
            return;
        }
        try {
            getConfig().save(this.customFile);
        } catch (IOException ex) {
            LightSource.getInstance().getLogger().log(Level.SEVERE, "Could not save config to " + this.customFile, ex);
        }
    }

    public static void addLightSource(LightItem item, String keyName) {
        if (!list.contains(item)) {
            list.add(item);
            LightSource.getInstance().log(LightSource.getInstance().BUKKIT_SENDER, "Added new item: " + ChatColor.YELLOW + keyName);
        } else {
            plugin.getLogger().log(Level.WARNING, "This item is already in the list.");
        }
    }

    public static void removeLightSource(LightItem item) {
        Iterator<LightItem> it = getList().iterator();
        while (it.hasNext()) {
            LightItem litem = it.next();
            if (item.getId().equals(litem.getId())) {
                customConfig.set(item.getId(), null);
                it.remove();
            }
        }

        Iterator<Source> sources = LightSource.getInstance().getSourceManager().getSourceList().iterator();
        while (sources.hasNext()) {
            Source i = sources.next();
            if (i.getItem().getId().equals(item.getId())) {
                sources.remove();
            }
        }
    }

    public static ArrayList<LightItem> getList() {
        return list;
    }

    public static LightItem getLightItem(String id) {
        for (LightItem item : getList()) {
            if (item.getId().equals(id)) {
                return item.clone();
            }
        }
        return null;
    }

    public static LightItem getLightItem(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            if (item.getItemMeta().hasDisplayName()) {
                String name = item.getItemMeta().getDisplayName();
                Material mat = item.getType();

                for (LightItem items : getList()) {
                    if (name.equals(items.getName()) && mat == items.getMaterial()) {
                        return items.clone();// FOUND!
                    }
                }
            } else if (!item.getItemMeta().hasDisplayName()) {
                Material mat = item.getType();

                for (LightItem items : getList()) {
                    if (items.getName() == null && mat == items.getMaterial()) {
                        return items.clone();// FOUND!
                    }
                }
            }
        }
        return null;
    }

    public static boolean isLightSource(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            if (item.getItemMeta().hasDisplayName()) {
                String name = item.getItemMeta().getDisplayName();
                Material mat = item.getType();
                int data = item.getDurability();

                for (LightItem items : getList()) {
                    if (name.equals(items.getName()) && mat == items.getMaterial() && data == items.getData()) {
                        return true;// FOUND!
                    }
                }
            } else if (!item.getItemMeta().hasDisplayName()) {
                Material mat = item.getType();
                int data = item.getDurability();

                for (LightItem items : getList()) {
                    if (items.getName() == null && mat == items.getMaterial() && data == items.getData()) {
                        return true;// FOUND!
                    }
                }
            }
        }
        return false;
    }

}