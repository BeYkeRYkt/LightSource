package ykt.BeYkeRYkt.LightSource.HeadLamp;

import java.util.ArrayList;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.Config;
import ykt.BeYkeRYkt.LightSource.LightSource;

public class HeadManager {

	private static ArrayList<CustomHeadLight> list = new ArrayList<CustomHeadLight>();
	private static LightSource plugin = LightSource.getInstance();
	private Config conf;

	public HeadManager() {
		this.conf = new Config("HeadLamp");
	}

	public void loadItems() {
		for (String str : getConfig().getSourceConfig().getKeys(false)) {
			String name = getConfig().getSourceConfig().getString(str + ".name");
			
			if(name != null){
				name = ChatColor.translateAlternateColorCodes('&', name);
				}
			
			Material material = Material.getMaterial(getConfig()
					.getSourceConfig().getString(str + ".material"));
			int level = getConfig().getSourceConfig().getInt(
					str + ".lightlevel");
			
			CustomHeadLight item = new CustomHeadLight(name, material, level);

			addLightSource(item, str);
		}
	}

	public Config getConfig() {
		return conf;
	}

	public static void addLightSource(CustomHeadLight item, String keyName) {
		if (!list.contains(item)) {
			list.add(item);
			plugin.getLogger().info("Added new HeadLamp: " + keyName);
		} else {
			plugin.getLogger().log(Level.WARNING,
					"This headlamp is already in the list.");
		}
	}

	public static void removeLightSource(CustomHeadLight item) {
		if (list.contains(item)) {
			list.remove(item);
		} else {
			plugin.getLogger().log(Level.WARNING, "This source is not found.");
		}
	}

	public static ArrayList<CustomHeadLight> getList() {
		return list;
	}

	public static boolean isHeadLamp(ItemStack item) {
		if(item != null && item.getType() != Material.AIR){
		if (item.getItemMeta().hasDisplayName()) {
			String name = item.getItemMeta().getDisplayName();
			Material mat = item.getType();

			for (CustomHeadLight items : getList()) {
				if (name.equals(items.getName()) && mat == items.getMaterial()) {
					return true;// FOUND!
				}
			}
		} else if (!item.getItemMeta().hasDisplayName()) {
			Material mat = item.getType();

			for (CustomHeadLight items : getList()) {
				if (items.getName() == null && mat == items.getMaterial()) {
					return true;// FOUND!
				}
			}
		}
		}
		return false;
	}

	public static int getLightLevel(ItemStack item) {
		if(item != null && item.getType() != Material.AIR){
		if (item.getItemMeta().hasDisplayName()) {
			String name = item.getItemMeta().getDisplayName();
			Material mat = item.getType();

			for (CustomHeadLight items : getList()) {
				if (name.equals(items.getName()) && mat == items.getMaterial()) {

					if (LightSource.getInstance().getConfig()
							.getBoolean("Debug")) {
						LightSource.getInstance().getLogger()
								.info("Light level :" + items.getLevelLight());
					}

					return items.getLevelLight();// FOUND!
				}
			}
		} else if (!item.getItemMeta().hasDisplayName()) {
			Material mat = item.getType();

			for (CustomHeadLight items : getList()) {
				if (items.getName() == null && mat == items.getMaterial()) {

					if (LightSource.getInstance().getConfig()
							.getBoolean("Debug")) {
						LightSource.getInstance().getLogger()
								.info("Light level :" + items.getLevelLight());
					}

					return items.getLevelLight();// FOUND!
				}
			}
		}
		}
		return 0;
	}

}