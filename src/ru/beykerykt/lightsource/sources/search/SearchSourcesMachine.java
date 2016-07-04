package ru.beykerykt.lightsource.sources.search;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSource;
import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.ItemSlot;
import ru.beykerykt.lightsource.items.flags.FlagExecutor;
import ru.beykerykt.lightsource.items.flags.RequirementFlagExecutor;
import ru.beykerykt.lightsource.sources.InventorySlotSource;
import ru.beykerykt.lightsource.sources.Source;

public class SearchSourcesMachine implements Runnable {

	private List<IgnoreEntityEntry> ignoreList = null;

	private boolean isStarted;
	private int id;

	public void start(int ticks) {
		if (!isStarted) {
			ignoreList = new CopyOnWriteArrayList<IgnoreEntityEntry>();
			isStarted = true;
			id = Bukkit.getScheduler().runTaskTimer(LightSource.getInstance(), this, 0, ticks).getTaskId();
		}
	}

	public void shutdown() {
		if (isStarted) {
			isStarted = false;
			ignoreList.clear();
			Bukkit.getScheduler().cancelTask(id);
		}
	}

	public boolean callRequirementFlags(Entity entity, ItemStack itemStack, Item item, ItemSlot slot) {
		for (String flag : item.getFlagsList()) {
			String[] args = flag.split(":").clone();
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

		// search
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (LightSourceAPI.getSourceManager().isSource(entity))
					continue;
				if (entity.getType().isAlive()) {
					LivingEntity le = (LivingEntity) entity;

					// all armor set
					if (le.getEquipment().getArmorContents().length == 0)
						continue;
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
						if (callRequirementFlags(le, itemStack, item, slot)) {
							Source source = new InventorySlotSource(le, item, slot);
							LightSourceAPI.getSourceManager().addSource(source);
						}
					}

					// Main item hand
					ItemStack itemStack = le.getEquipment().getItemInMainHand();
					if (itemStack == null || itemStack.getType() == Material.AIR)
						continue;
					if (!LightSourceAPI.getItemManager().isItem(itemStack))
						continue;
					Item item = LightSourceAPI.getItemManager().getItemFromItemStack(itemStack);
					if (item.getFlagsList().isEmpty())
						continue;
					if (callRequirementFlags(le, itemStack, item, ItemSlot.RIGHT_HAND)) {
						Source source = new InventorySlotSource(le, item, ItemSlot.RIGHT_HAND);
						LightSourceAPI.getSourceManager().addSource(source);
					}

					// Off item hand
					ItemStack itemStackOff = le.getEquipment().getItemInOffHand();
					if (itemStackOff == null || itemStackOff.getType() == Material.AIR) {
						continue;
					}
					if (!LightSourceAPI.getItemManager().isItem(itemStackOff))
						continue;
					Item itemOff = LightSourceAPI.getItemManager().getItemFromItemStack(itemStackOff);
					if (itemOff.getFlagsList().isEmpty())
						continue;
					if (callRequirementFlags(le, itemStackOff, itemOff, ItemSlot.LEFT_HAND)) {
						Source source = new InventorySlotSource(le, itemOff, ItemSlot.LEFT_HAND);
						LightSourceAPI.getSourceManager().addSource(source);
					}
				}
			}
		}

	}
}
