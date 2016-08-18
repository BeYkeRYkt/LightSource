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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.ItemSlot;
import ru.beykerykt.lightsource.items.flags.FlagExecutor;
import ru.beykerykt.lightsource.items.flags.RequirementFlagExecutor;

public class SearchMachine implements Runnable {

	private boolean isStarted;
	private List<SearchTask> tasks = new CopyOnWriteArrayList<SearchTask>();
	private List<IgnoreEntityEntry> ignoreList = new CopyOnWriteArrayList<IgnoreEntityEntry>();
	private ScheduledFuture<?> sch;

	public void start(int ticks) {
		if (!isStarted) {
			isStarted = true;
			sch = LightSourceAPI.getSchedulerExecutor().scheduleWithFixedDelay(this, 0, 50 * ticks, TimeUnit.MILLISECONDS);
		}
	}

	public void shutdown() {
		if (isStarted) {
			isStarted = false;
			tasks.clear();
			ignoreList.clear();
			sch.cancel(false);
		}
	}

	public void addTask(SearchTask task) {
		tasks.add(task);
	}

	public boolean callRequirementFlags(Entity entity, ItemStack itemStack, Item item, ItemSlot slot) {
		// if entity is not alive (example item), slot can be null
		for (String flag : item.getFlagsList()) {
			String[] args = flag.split(":").clone();
			if (!LightSourceAPI.getFlagManager().hasFlag(args[0])) {
				LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Sorry, but the flag of " + ChatColor.WHITE + args[0] + ChatColor.RED + " is not found. This tag will not be processed flag system.");
				item.getFlagsList().remove(flag);
				continue;
			}
			FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0]);
			args = (String[]) ArrayUtils.remove(args, 0);
			if (!(executor instanceof RequirementFlagExecutor)) {
				continue;
			}
			RequirementFlagExecutor rfe = (RequirementFlagExecutor) executor;
			if (!rfe.onCheckRequirement(entity, itemStack, item, args)) {
				IgnoreEntityEntry entry = null;
				if (entity.getType().isAlive()) {
					entry = new IgnoreEntityLivingEntry(entity, itemStack, slot);
				} else {
					entry = new IgnoreEntityEntry(entity, itemStack);
				}
				if (!ignoreList.contains(entry)) {
					rfe.onCheckingFailure(entity, itemStack, item, args);
					ignoreList.add(entry);
				}
				entry = null; // seriously :/ ?
				return false;
			}
			rfe.onCheckingSuccess(entity, itemStack, item, args);
		}
		return true;
	}

	@Override
	public void run() {
		// ignoreList
		for (IgnoreEntityEntry iee : ignoreList) {
			// Basic
			if (iee.getEntity().isDead()) {
				ignoreList.remove(iee);
				continue;
			}

			if (LightSourceAPI.getSourceManager().isSource(iee.getEntity())) {
				ignoreList.remove(iee);
				continue;
			}

			// If entity is alive type (Player, sheep and etc)
			if (iee.getEntity().getType().isAlive()) {
				LivingEntity le = (LivingEntity) iee.getEntity();
				ItemSlot slot = ((IgnoreEntityLivingEntry) iee).getSlot();

				if (slot == ItemSlot.RIGHT_HAND) {
					// Main item hand
					ItemStack itemStackMainHand = le.getEquipment().getItemInMainHand();
					if (itemStackMainHand == null || itemStackMainHand.getType() == Material.AIR) {
						ignoreList.remove(iee);
						continue;
					}

					if (!itemStackMainHand.equals(iee.getItemStack())) {
						ignoreList.remove(iee);
						continue;
					}
				} else if (slot == ItemSlot.LEFT_HAND) {
					// Off item hand
					ItemStack itemStackOffHand = le.getEquipment().getItemInOffHand();
					if (itemStackOffHand == null || itemStackOffHand.getType() == Material.AIR) {
						ignoreList.remove(iee);
						continue;
					}

					if (!itemStackOffHand.equals(iee.getItemStack())) {
						ignoreList.remove(iee);
						continue;
					}
				} else {
					// armor set
					ItemStack itemStack = le.getEquipment().getArmorContents()[ItemSlot.getArmorContentFromItemSlot(((IgnoreEntityLivingEntry) iee).getSlot())];

					if (itemStack == null || itemStack.getType() == Material.AIR) {
						ignoreList.remove(iee);
						continue;
					}

					if (!itemStack.equals(iee.getItemStack())) {
						ignoreList.remove(iee);
						continue;
					}
				}
			}
		}

		for (SearchTask task : tasks) {
			task.onTick();
		}
	}
}
