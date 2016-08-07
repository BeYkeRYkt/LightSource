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

public class EntitySearchMachine implements SearchTask {

	private double radius = 0;

	public EntitySearchMachine(double radius) {
		this.radius = radius;
	}

	@Override
	public void onTick() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
				if (LightSourceAPI.getSourceManager().isSource(entity))
					continue;
				if (entity.getType().isAlive() && entity.getType() != EntityType.PLAYER) {
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
						if (LightSourceAPI.getSearchMachine().callRequirementFlags(le, itemStack, item, slot)) {
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
					if (LightSourceAPI.getSearchMachine().callRequirementFlags(le, itemStack, item, ItemSlot.RIGHT_HAND)) {
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
					if (LightSourceAPI.getSearchMachine().callRequirementFlags(le, itemStackOff, itemOff, ItemSlot.LEFT_HAND)) {
						Source source = new InventorySlotSource(le, itemOff, ItemSlot.LEFT_HAND);
						LightSourceAPI.getSourceManager().addSource(source);
					}
				}
			}
		}
	}

}
