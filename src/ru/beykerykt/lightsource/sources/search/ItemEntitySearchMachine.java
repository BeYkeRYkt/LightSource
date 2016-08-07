package ru.beykerykt.lightsource.sources.search;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.flags.FlagHelper;
import ru.beykerykt.lightsource.sources.EntityItemSource;
import ru.beykerykt.lightsource.sources.Source;

public class ItemEntitySearchMachine implements SearchTask {

	private double radius = 20;

	public ItemEntitySearchMachine(double radius) {
		this.radius = radius;
	}

	@Override
	public void onTick() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
				if (LightSourceAPI.getSourceManager().isSource(entity))
					continue;
				if (entity.getType() == EntityType.DROPPED_ITEM) {
					org.bukkit.entity.Item ie = (org.bukkit.entity.Item) entity;
					ItemStack itemStack = ie.getItemStack();
					if (itemStack == null || itemStack.getType() == Material.AIR)
						continue;
					if (!LightSourceAPI.getItemManager().isItem(itemStack))
						continue;
					Item item = LightSourceAPI.getItemManager().getItemFromItemStack(itemStack);
					if (item.getFlagsList().isEmpty())
						continue;
					if (FlagHelper.callRequirementFlags(ie, itemStack, item, false)) { // ?
						Source source = new EntityItemSource(ie, item);
						LightSourceAPI.getSourceManager().addSource(source);
					}
				}
			}
		}
	}
}
