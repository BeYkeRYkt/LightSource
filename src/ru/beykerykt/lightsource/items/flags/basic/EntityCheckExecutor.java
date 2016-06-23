package ru.beykerykt.lightsource.items.flags.basic;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.flags.RequirementFlagExecutor;

public class EntityCheckExecutor implements RequirementFlagExecutor {

	@Override
	public boolean onCheckRequirement(Entity entity, ItemStack itemStack, Item item, String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				String entityType = args[i];
				if (entity.getType() == EntityType.valueOf(entityType.toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}

}
