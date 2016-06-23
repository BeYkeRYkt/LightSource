package ru.beykerykt.lightsource.items;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;

public class ItemManager {

	private Map<String, Item> items;

	public ItemManager() {
		this.items = new ConcurrentHashMap<String, Item>();
	}

	public boolean addItem(Item item) {
		if (items.containsKey(item.getId())) {
			LightSourceAPI.log(Bukkit.getConsoleSender(), "This item is already in the list.");
			return false;
		}
		items.put(item.getId(), item);
		LightSourceAPI.log(Bukkit.getConsoleSender(), "Added new item: " + ChatColor.YELLOW + item.getId());
		return true;
	}

	public boolean removeItem(String id) {
		if (!items.containsKey(id)) {
			LightSourceAPI.log(Bukkit.getConsoleSender(), "This id is not on the list.");
			return false;
		}
		items.remove(id);
		LightSourceAPI.log(Bukkit.getConsoleSender(), "Revmoed item: " + ChatColor.YELLOW + id);
		return true;
	}

	public Map<String, Item> getItems() {
		return items;
	}

	public Item createItemFromConfig(FileConfiguration file, String id) {
		if (file == null)
			return null;

		// Load basic info
		Material material = Material.getMaterial(file.getString(id + ".material"));
		int data = file.getInt(id + ".data");
		int level = file.getInt(id + ".lightlevel");

		Item item = new Item(id, material, data, level);

		// Load other info
		String displayName = file.getString(id + ".displayName");
		if (displayName != null) {
			displayName = ChatColor.translateAlternateColorCodes('&', displayName);
			item.setDisplayName(displayName);
		}

		@SuppressWarnings("unchecked")
		List<String> tags = (List<String>) file.getList(id + ".tags");
		if (tags != null && !tags.isEmpty()) {
			item.getFlagsList().addAll(tags);
		}
		return item;
	}

	public boolean isItem(ItemStack item) {
		if (item == null)
			return false;
		if (item.hasItemMeta()) {
			if (item.getItemMeta().hasDisplayName()) {
				return checkItemWithDisplayName(item) != null;
			}
			return checkItemWithoutDisplayName(item) != null;
		}
		return checkItemWithoutDisplayName(item) != null;
	}

	public Item getItemFromItemStack(ItemStack item) {
		if (item.hasItemMeta()) {
			if (item.getItemMeta().hasDisplayName()) {
				return checkItemWithDisplayName(item);
			}
			return checkItemWithoutDisplayName(item);
		}
		return checkItemWithoutDisplayName(item);
	}

	public Item getItemFromId(String id) {
		return getItems().get(id);
	}

	private Item checkItemWithDisplayName(ItemStack item) {
		for (Item i : getItems().values()) {
			if (i.getMaterial() == item.getType() && i.getData() == item.getDurability() && i.getDisplayName().equals(item.getItemMeta().getDisplayName())) {
				return i;
			}
		}
		return null;
	}

	private Item checkItemWithoutDisplayName(ItemStack item) {
		for (Item i : getItems().values()) {
			if (i.getMaterial() == item.getType() && i.getData() == item.getDurability()) {
				return i;
			}
		}
		return null;
	}
}
