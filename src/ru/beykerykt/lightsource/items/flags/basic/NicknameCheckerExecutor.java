package ru.beykerykt.lightsource.items.flags.basic;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.flags.EndingFlagExecutor;
import ru.beykerykt.lightsource.items.flags.RequirementFlagExecutor;
import ru.beykerykt.lightsource.items.flags.UpdatableFlagExecutor;
import ru.beykerykt.lightsource.sources.ItemableSource;
import ru.beykerykt.lightsource.sources.LivingOwnedSource;

public class NicknameCheckerExecutor implements RequirementFlagExecutor, UpdatableFlagExecutor, EndingFlagExecutor {

	/**
	 * Describes how to use of this flag. The method returns only one line.
	 */
	@Override
	public String getDescription() {
		return "nicknamecheck:[player name]";
	}

	/**
	 * The maximum number of arguments. It is informative only.
	 */
	@Override
	public int getMaxArgs() {
		return 1;
	}

	/**
	 * Method from RequirementFlagExecutor. The method is to check all requirements before registering the light source. We need only return answer any messages and other activities that require a one-time output.
	 */
	@Override
	public boolean onCheckRequirement(Entity entity, ItemStack itemStack, Item item, String[] args) {
		return entity.getName().equals(args[0]);
	}

	/**
	 * Method from RequirementFlagExecutor. If onCheckRequirement() gives a positive response - this method is called. This method is called once after verification.
	 */
	@Override
	public void onCheckingSuccess(Entity entity, ItemStack itemStack, Item item, String[] args) {
		entity.sendMessage("OMAGE! YOU ARE " + args[0]);
	}

	/**
	 * Method from RequirementFlagExecutor. If onCheckRequirement() gives a negative response - this method is called. This method is called once after verification.
	 */
	@Override
	public void onCheckingFailure(Entity entity, ItemStack itemStack, Item item, String[] args) {
		// TODO Auto-generated method stub
	}

	/**
	 * Method from EndingFlagExecutor. Called when the light source ceases to exist. It called once.
	 */
	@Override
	public void onEnd(ItemableSource source, String[] args) {
		source.getLocation().getWorld().strikeLightning(source.getLocation());
	}

	/**
	 * Method from UpdatableFlagExecutor. Called after every tick update in update-runnable. It called once during "tick"
	 */
	@Override
	public void onUpdate(ItemableSource source, String[] args) {
		if (source instanceof LivingOwnedSource) {
			LivingOwnedSource s = (LivingOwnedSource) source;
			s.getOwner().setHealth(s.getOwner().getMaxHealth());
		}
	}
}
