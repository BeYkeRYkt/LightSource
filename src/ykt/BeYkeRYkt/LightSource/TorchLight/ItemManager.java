package ykt.BeYkeRYkt.LightSource.TorchLight;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.Config;
import ykt.BeYkeRYkt.LightSource.LightSource;

public class ItemManager {

	private static ArrayList<CustomItemLight> list = new ArrayList<CustomItemLight>();
	private static LightSource plugin = LightSource.getInstance();
	private Config conf;

	public ItemManager() {
		this.conf = new Config("TorchLight");
	}

	public void loadItems() {
		for (String str : getConfig().getSourceConfig().getKeys(false)) {
			
			String name = getConfig().getSourceConfig().getString(str + ".name");
			
			if(name != null){
			name = ChatColor.translateAlternateColorCodes('&', name);
			}
			
			Material material = Material.getMaterial(getConfig().getSourceConfig().getString(str + ".material"));
			int level = getConfig().getSourceConfig().getInt(str + ".lightlevel");

			CustomItemLight item = new CustomItemLight(name, material, level);

			addLightSource(item, str);
		}
	}

	public Config getConfig() {
		return conf;
	}

	public static void addLightSource(CustomItemLight item, String keyName) {
		if (!list.contains(item)) {
			list.add(item);
			plugin.getLogger().info("Added new TorchLight: " + keyName);
		} else {
			plugin.getLogger().log(Level.WARNING,
					"This source is already in the list.");
		}
	}

	public static void removeLightSource(CustomItemLight item) {
		if (list.contains(item)) {
			list.remove(item);
		} else {
			plugin.getLogger().log(Level.WARNING, "This source is not found.");
		}
	}

	public static ArrayList<CustomItemLight> getList() {
		return list;
	}

	public static boolean isTorchLight(ItemStack item) {
		if (item.getItemMeta().hasDisplayName()) {
			String name = item.getItemMeta().getDisplayName();
			Material mat = item.getType();

			for (CustomItemLight items : getList()) {
				if (name.equals(items.getName()) && mat == items.getMaterial()) {
					return true;// FOUND!
				}
			}
			// Name = null;
		} else if (!item.getItemMeta().hasDisplayName()) {
			Material mat = item.getType();
			for (CustomItemLight items : getList()) {
				if (mat == items.getMaterial() && items.getName() == null) {
					return true;// FOUND!
				}
			}
		}

		return false;
	}

	public static int getLightLevel(ItemStack item) {
		if (item.getItemMeta().hasDisplayName()) {
			String name = item.getItemMeta().getDisplayName();
			Material mat = item.getType();

			for (CustomItemLight items : getList()) {
				if (name.equals(items.getName()) && mat == items.getMaterial()) {

					if (LightSource.getInstance().getConfig()
							.getBoolean("Debug")) {
						LightSource.getInstance().getLogger()
								.info("Light level :" + items.getLevelLight());
					}

					return items.getLevelLight();// FOUND!
				}
			}
			// Name = null;
		} else if (!item.getItemMeta().hasDisplayName()) {
			Material mat = item.getType();
			for (CustomItemLight items : getList()) {
				if (mat == items.getMaterial() && items.getName() == null) {

					if (LightSource.getInstance().getConfig()
							.getBoolean("Debug")) {
						LightSource.getInstance().getLogger()
								.info("Light level :" + items.getLevelLight());
					}

					return items.getLevelLight();// FOUND!
				}
			}
		}

		return 0;
	}

}