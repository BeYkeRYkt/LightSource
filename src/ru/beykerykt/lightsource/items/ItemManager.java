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
package ru.beykerykt.lightsource.items;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;

public class ItemManager {

	private Map<String, Item> items;

	public ItemManager() {
		this.items = new ConcurrentHashMap<String, Item>();
	}

	public boolean addItem(Item item) {
		if (items.containsKey(item.getId())) {
			LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "This item is already in the list.");
			return false;
		}
		items.put(item.getId(), item);
		LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "Added new item: " + ChatColor.YELLOW + item.getId());
		return true;
	}

	public boolean removeItem(String id) {
		if (!items.containsKey(id)) {
			LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "This id is not on the list.");
			return false;
		}
		items.remove(id);
		LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), "Removed item: " + ChatColor.YELLOW + id);
		return true;
	}

	public Map<String, Item> getItems() {
		return items;
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
