/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 - 2016
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.beykerykt.lightsource.items.loader;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
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
		String displayName = file.getString(id + ".displayname");
		if (displayName != null) {
			displayName = ChatColor.translateAlternateColorCodes('&', displayName);
			item.setDisplayName(displayName);
		}

		@SuppressWarnings("unchecked")
		List<String> flags = (List<String>) file.getList(id + ".flags");
		if (flags != null && !flags.isEmpty()) {
			// item.getFlagsList().addAll(tags);
			for (int i = 0; i < flags.size(); i++) {
				String flagLine = flags.get(i);
				String[] args = flagLine.split(":").clone();
				if (!LightSourceAPI.getFlagManager().hasFlag(args[0])) {
					LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Sorry, but the flag of " + ChatColor.WHITE + args[0] + ChatColor.RED + " is not found. This tag will not be processed flag system.");
					continue;
				}
				item.getFlagsList().add(flagLine);
			}
		}
		return item;
	}
}
