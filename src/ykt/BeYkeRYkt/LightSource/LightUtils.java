package ykt.BeYkeRYkt.LightSource;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LightUtils {

	public static ItemStack setFuelLore(ItemStack item, int percent) {
		if (item.hasItemMeta()) {
			if (item.getItemMeta().hasLore()) {
				ItemMeta meta = item.getItemMeta();
				List<String> list = meta.getLore();
				int index = list.size() + 1;

				for (String line : list) {
					if (line.startsWith(ChatColor.GOLD + "Fuel: ")) {
						index = list.indexOf(line);
						break;
					}
				}

				list.set(index, ChatColor.GOLD + "Fuel: " + ChatColor.GREEN + percent + ChatColor.GOLD + " percent");
				meta.setLore(list);
				item.setItemMeta(meta);
			} else {
				ItemMeta meta = item.getItemMeta();
				List<String> list = new ArrayList<String>();
				list.add(ChatColor.GOLD + "Fuel: " + ChatColor.GREEN + percent + ChatColor.GOLD + " percent");
				meta.setLore(list);
				item.setItemMeta(meta);
			}
		} else {
			ItemMeta meta = item.getItemMeta();
			List<String> list = new ArrayList<String>();
			list.add(ChatColor.GOLD + "Fuel: " + ChatColor.GREEN + percent + ChatColor.GOLD + " percent");
			meta.setLore(list);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static int getFuelLore(ItemStack item) {
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();

			if (lore != null && !lore.isEmpty()) {
				for (String line : lore) {
					if (line.startsWith(ChatColor.GOLD + "Fuel: ")) {
						String splitted = line.split("Fuel: " + ChatColor.GREEN)[1];
						splitted = splitted.split(" percent")[0];
						int percent = Integer.parseInt(ChatColor.stripColor(splitted));
						return percent;
					}
				}
			}
		}

		return 100;
	}

}