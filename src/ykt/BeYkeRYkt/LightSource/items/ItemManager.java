package ykt.BeYkeRYkt.LightSource.items;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightSource;

public class ItemManager {

	private FileConfiguration customConfig = null;
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
			int level = getConfig().getInt(str + ".lightlevel");
			int burnTime = getConfig().getInt(str + ".burnTime");

			LightItem item = new LightItem(str, name, material, level, burnTime);

			addLightSource(item, str);
		}
	}

	public void reloadConfig() {
		if (this.customFile == null) {
			String folder = LightSource.getInstance().getDataFolder() + "";
			this.customFile = new File(folder, name + ".yml");
		}
		this.customConfig = YamlConfiguration.loadConfiguration(this.customFile);

		// Look for defaults in the jar
		InputStream defConfigStream = LightSource.getInstance().getResource(name + ".yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			this.customConfig.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig() {
		if (this.customConfig == null) {
			reloadConfig();
		}
		return this.customConfig;
	}

	public void saveConfig() {
		if (this.customConfig == null || this.customFile == null) {
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
			plugin.getAPI().log(plugin.getAPI().BUKKIT_SENDER, "Added new item: " + ChatColor.YELLOW + keyName);
		} else {
			plugin.getLogger().log(Level.WARNING, "This item is already in the list.");
		}
	}

	public static void removeLightSource(LightItem item) {
		if (list.contains(item)) {
			list.remove(item);
		} else {
			plugin.getLogger().log(Level.WARNING, "This source is not found.");
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

				for (LightItem items : getList()) {
					if (name.equals(items.getName()) && mat == items.getMaterial()) {
						return true;// FOUND!
					}
				}
			} else if (!item.getItemMeta().hasDisplayName()) {
				Material mat = item.getType();

				for (LightItem items : getList()) {
					if (items.getName() == null && mat == items.getMaterial()) {
						return true;// FOUND!
					}
				}
			}
		}
		return false;
	}

	public static int getLightLevel(ItemStack item) {
		if (item != null && item.getType() != Material.AIR) {
			if (item.getItemMeta().hasDisplayName()) {
				String name = item.getItemMeta().getDisplayName();
				Material mat = item.getType();

				for (LightItem items : getList()) {
					if (name.equals(items.getName()) && mat == items.getMaterial()) {
						return items.getLevelLight();// FOUND!
					}
				}
			} else if (!item.getItemMeta().hasDisplayName()) {
				Material mat = item.getType();

				for (LightItem items : getList()) {
					if (items.getName() == null && mat == items.getMaterial()) {
						return items.getLevelLight();// FOUND!
					}
				}
			}
		}
		return 0;
	}

}