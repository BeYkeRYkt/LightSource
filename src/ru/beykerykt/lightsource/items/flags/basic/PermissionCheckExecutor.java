package ru.beykerykt.lightsource.items.flags.basic;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.flags.RequirementFlagExecutor;

public class PermissionCheckExecutor implements RequirementFlagExecutor {

	@Override
	public boolean onCheckRequirement(Entity entity, ItemStack itemStack, Item item, String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				String permission = args[i];
				if (!entity.hasPermission(permission)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void onCheckingSuccess(Entity entity, ItemStack itemStack, Item item, String[] args) {
	}

	@Override
	public void onCheckingFailure(Entity entity, ItemStack itemStack, Item item, String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				String permission = args[i];
				if (!entity.hasPermission(permission)) {
					LightSourceAPI.sendMessage(entity, ChatColor.RED + "You don't have permission: " + ChatColor.WHITE + permission);
				}
			}
		}
	}
}
