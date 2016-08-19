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
			try (FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0])) {
				args = (String[]) ArrayUtils.remove(args, 0);
				if (!(executor instanceof RequirementFlagExecutor)) {
					continue;
				}
				// resource warning
				try (RequirementFlagExecutor rfe = (RequirementFlagExecutor) executor) {
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			try (FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0])) {
				args = (String[]) ArrayUtils.remove(args, 0);
				if (!(executor instanceof UpdatableFlagExecutor)) {
					continue;
				}
				// resource warning
				try (UpdatableFlagExecutor tfe = (UpdatableFlagExecutor) executor) {
					tfe.onUpdate(source, args);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			try (FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0])) {
				args = (String[]) ArrayUtils.remove(args, 0);
				if (!(executor instanceof EndingFlagExecutor)) {
					continue;
				}
				// resource warning
				try (EndingFlagExecutor efe = (EndingFlagExecutor) executor) {
					efe.onEnd(source, args);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
