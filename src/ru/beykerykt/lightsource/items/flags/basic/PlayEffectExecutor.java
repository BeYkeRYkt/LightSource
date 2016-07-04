package ru.beykerykt.lightsource.items.flags.basic;

import org.bukkit.Effect;

import ru.beykerykt.lightsource.items.flags.TickableFlagExecutor;
import ru.beykerykt.lightsource.sources.Source;

public class PlayEffectExecutor implements TickableFlagExecutor {

	@Override
	public void onTick(Source source, String[] args) {
		if (args.length >= 2) {
			String effectName = args[0];
			int data = Integer.parseInt(args[1]);
			source.getLocation().getWorld().playEffect(source.getLocation(), Effect.getByName(effectName.toUpperCase()), data);
		} else {
			// default ?
			source.getLocation().getWorld().playEffect(source.getLocation(), Effect.SMOKE, 0);
		}
	}

}
