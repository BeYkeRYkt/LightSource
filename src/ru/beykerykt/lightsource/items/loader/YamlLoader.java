package ru.beykerykt.lightsource.items.loader;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;

public class YamlLoader implements ItemLoader {

	@Override
	public void loadFromFile(File file) {
		if (file == null)
			return;
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		for (String str : config.getKeys(false)) {
			Item item = createItemFromConfig(config, str);
			LightSourceAPI.getItemManager().addItem(item);
		}
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
		List<String> tags = (List<String>) file.getList(id + ".flags");
		if (tags != null && !tags.isEmpty()) {
			item.getFlagsList().addAll(tags);
		}
		return item;
	}
}
