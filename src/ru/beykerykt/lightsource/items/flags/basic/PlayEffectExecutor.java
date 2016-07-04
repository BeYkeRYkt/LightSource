package ru.beykerykt.lightsource.items.flags.basic;

import org.bukkit.Effect;

import ru.beykerykt.lightsource.items.flags.TickableFlagExecutor;
import ru.beykerykt.lightsource.sources.Source;

public class PlayEffectExecutor implements TickableFlagExecutor {

	@Override
	public void onTick(Source source, String[] args) {
		if (args.length >= 3) {
			String effectName = args[0];
			int data = Integer.parseInt(args[1]);
			int count = Integer.parseInt(args[2]);
			source.getLocation().getWorld().spigot().playEffect(source.getLocation(), Effect.valueOf(effectName.toUpperCase()), 0, data, 0, 0, 0, 0, count, 10);
		} else {
			// default ?
			source.getLocation().getWorld().spigot().playEffect(source.getLocation(), Effect.PARTICLE_SMOKE, 0, 0, 0, 0, 0, 0, 1, 10);
		}
	}

}
