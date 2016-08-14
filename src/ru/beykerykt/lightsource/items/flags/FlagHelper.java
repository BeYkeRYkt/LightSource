package ru.beykerykt.lightsource.items.flags;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.sources.ItemableSource;

public class FlagHelper {

	public static boolean callRequirementFlags(Entity entity, ItemStack itemStack, Item item, boolean onlyCheck) {
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
				if (!onlyCheck) {
					rfe.onCheckingFailure(entity, itemStack, item, args);
				}
				return false;
			}
			if (!onlyCheck) {
				rfe.onCheckingSuccess(entity, itemStack, item, args);
			}
		}
		return true;
	}

	public static void callUpdateFlag(ItemableSource source) {
		for (String flag : source.getItem().getFlagsList()) {
			String[] args = flag.split(":").clone();
			if (!LightSourceAPI.getFlagManager().hasFlag(args[0])) {
				LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Sorry, but the flag of " + ChatColor.WHITE + args[0] + ChatColor.RED + " is not found. This tag will not be processed flag system.");
				source.getItem().getFlagsList().remove(flag);
				continue;
			}
			FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0]);
			args = (String[]) ArrayUtils.remove(args, 0);
			if (executor instanceof TickableFlagExecutor) {
				TickableFlagExecutor tfe = (TickableFlagExecutor) executor;
				tfe.onTick(source, args);
			}
		}
	}

	public static void callEndingFlag(ItemableSource source) {
		for (String flag : source.getItem().getFlagsList()) {
			String[] args = flag.split(":").clone();
			if (!LightSourceAPI.getFlagManager().hasFlag(args[0])) {
				LightSourceAPI.sendMessage(Bukkit.getConsoleSender(), ChatColor.RED + "Sorry, but the flag of " + ChatColor.WHITE + args[0] + ChatColor.RED + " is not found. This tag will not be processed flag system.");
				source.getItem().getFlagsList().remove(flag);
				continue;
			}
			FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0]);
			args = (String[]) ArrayUtils.remove(args, 0);
			if (executor instanceof EndingFlagExecutor) {
				EndingFlagExecutor efe = (EndingFlagExecutor) executor;
				efe.onEnd(source, args);
			}
		}
	}
}
