package ru.beykerykt.lightsource.items.flags;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.items.Item;

public interface RequirementFlagExecutor extends FlagExecutor {

	public boolean onCheckRequirement(Entity entity, ItemStack itemStack, Item item, String[] args);

	public void execute(Entity entity, ItemStack itemStack, Item item);
}
