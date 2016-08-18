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
package ru.beykerykt.lightsource.sources.search;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.ItemSlot;
import ru.beykerykt.lightsource.sources.InventorySlotSource;
import ru.beykerykt.lightsource.sources.Source;

public class EntitySearchTask implements SearchTask {

	private double radius = 0;

	public EntitySearchTask(double radius) {
		this.radius = radius;
	}

	@Override
	public void onTick() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
				if (entity.getType().isAlive() && entity.getType() != EntityType.PLAYER) {
					if (LightSourceAPI.getSourceManager().isSource(entity))
						continue;
					LivingEntity le = (LivingEntity) entity;

					// Main item hand
					ItemStack itemStackHand = le.getEquipment().getItemInMainHand();
					if (itemStackHand != null && itemStackHand.getType() != Material.AIR) {
						if (LightSourceAPI.getItemManager().isItem(itemStackHand)) {
							Item item = LightSourceAPI.getItemManager().getItemFromItemStack(itemStackHand);
							if (!item.getFlagsList().isEmpty()) {
								if (LightSourceAPI.getSearchMachine().callRequirementFlags(le, itemStackHand, item, ItemSlot.RIGHT_HAND)) {
									Source source = new InventorySlotSource(le, item, itemStackHand, ItemSlot.RIGHT_HAND);
									LightSourceAPI.getSourceManager().addSource(source);
									continue;
								}
							}
						}
					}

					// Off item hand
					ItemStack itemStackOff = le.getEquipment().getItemInOffHand();
					if (itemStackOff != null && itemStackOff.getType() != Material.AIR) {
						if (LightSourceAPI.getItemManager().isItem(itemStackOff)) {
							Item itemOff = LightSourceAPI.getItemManager().getItemFromItemStack(itemStackOff);
							if (!itemOff.getFlagsList().isEmpty()) {
								if (LightSourceAPI.getSearchMachine().callRequirementFlags(le, itemStackOff, itemOff, ItemSlot.LEFT_HAND)) {
									Source source = new InventorySlotSource(le, itemOff, itemStackOff, ItemSlot.LEFT_HAND);
									LightSourceAPI.getSourceManager().addSource(source);
									continue;
								}
							}
						}
					}

					// all armor set
					if (le.getEquipment().getArmorContents().length != 0) {
						boolean found = false;
						for (int i = 0; i < le.getEquipment().getArmorContents().length; i++) {
							ItemStack itemStack = le.getEquipment().getArmorContents()[i];
							if (itemStack == null || itemStack.getType() == Material.AIR)
								continue;
							if (!LightSourceAPI.getItemManager().isItem(itemStack))
								continue;
							Item item = LightSourceAPI.getItemManager().getItemFromItemStack(itemStack);
							if (item.getFlagsList().isEmpty())
								continue;
							ItemSlot slot = ItemSlot.getItemSlotFromArmorContent(i);
							if (LightSourceAPI.getSearchMachine().callRequirementFlags(le, itemStack, item, slot)) {
								Source source = new InventorySlotSource(le, item, itemStack, slot);
								LightSourceAPI.getSourceManager().addSource(source);
								found = true;
							}
						}

						if (found) {
							continue;
						}
					}
				}
			}
		}
	}
}
