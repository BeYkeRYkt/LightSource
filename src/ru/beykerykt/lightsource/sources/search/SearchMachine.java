package ru.beykerykt.lightsource.sources.search;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSource;
import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.ItemSlot;
import ru.beykerykt.lightsource.items.flags.FlagExecutor;
import ru.beykerykt.lightsource.items.flags.RequirementFlagExecutor;

public class SearchMachine implements Runnable {

	private boolean isStarted;
	private int id;
	private List<SearchTask> tasks = new CopyOnWriteArrayList<SearchTask>();
	private List<IgnoreEntityEntry> ignoreList = new CopyOnWriteArrayList<>();

	public void start(int ticks) {
		if (!isStarted) {
			isStarted = true;
			id = Bukkit.getScheduler().runTaskTimerAsynchronously(LightSource.getInstance(), this, 0, ticks).getTaskId();
		}
	}

	public void shutdown() {
		if (isStarted) {
			isStarted = false;
			tasks.clear();
			ignoreList.clear();
			Bukkit.getScheduler().cancelTask(id);
		}
	}

	public void addTask(SearchTask task) {
		tasks.add(task);
	}

	public boolean callRequirementFlags(Entity entity, ItemStack itemStack, Item item, ItemSlot slot) {
		for (String flag : item.getFlagsList()) {
			String[] args = flag.split(":").clone();
			if (!LightSourceAPI.getFlagManager().hasFlag(args[0])) {
				LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Sorry, but the flag of " + ChatColor.WHITE + args[0] + ChatColor.RED + " is not found. This tag will not be processed flag system.");
				item.getFlagsList().remove(args[0]);
				continue;
			}
			FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0]);
			args = (String[]) ArrayUtils.remove(args, 0);
			if (executor instanceof RequirementFlagExecutor) {
				RequirementFlagExecutor rfe = (RequirementFlagExecutor) executor;
				if (!rfe.onCheckRequirement(entity, itemStack, item, args)) {
					IgnoreEntityEntry entry = new IgnoreEntityEntry(entity, itemStack, slot);
					if (!ignoreList.contains(entry)) {
						rfe.onCheckingFailure(entity, itemStack, item, args);
						ignoreList.add(entry);
					}
					entry = null; // seriously :/ ?
					return false;
				}
				rfe.onCheckingSuccess(entity, itemStack, item, args);
			}
		}
		return true;
	}

	@Override
	public void run() {
		// ignoreList
		for (IgnoreEntityEntry iee : ignoreList) {
			if (iee.getEntity().isDead()) {
				ignoreList.remove(iee);
				continue;
			}

			if (LightSourceAPI.getSourceManager().isSource(iee.getEntity())) {
				ignoreList.remove(iee);
				continue;
			}

			if (iee.getEntity().getType().isAlive()) {
				LivingEntity le = (LivingEntity) iee.getEntity();
				ItemSlot slot = iee.getSlot();

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
					ItemStack itemStack = le.getEquipment().getArmorContents()[ItemSlot.getArmorContentFromItemSlot(iee.getSlot())];

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
