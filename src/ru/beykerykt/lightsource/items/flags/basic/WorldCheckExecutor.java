package ru.beykerykt.lightsource.items.flags.basic;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.flags.RequirementFlagExecutor;

public class WorldCheckExecutor implements RequirementFlagExecutor {

	@Override
	public boolean onCheckRequirement(Entity entity, ItemStack itemStack, Item item, String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				String world = args[i];
				if (entity.getWorld().getName().equals(world)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onCheckingSuccess(Entity entity, ItemStack itemStack, Item item, String[] args) {
	}

	@Override
	public void onCheckingFailure(Entity entity, ItemStack itemStack, Item item, String[] args) {
		LightSourceAPI.sendMessage(entity, ChatColor.RED + "Sorry, but in this world you can not use this item. :(");
		LightSourceAPI.sendMessage(entity, ChatColor.GREEN + "You can use it in the following worlds");
		for (int i = 0; i < args.length; i++) {
			String world = args[i];
			LightSourceAPI.sendMessage(entity, world);
		}
	}

}
