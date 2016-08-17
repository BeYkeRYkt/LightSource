package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.Entity;

public class BurningSource extends OwnedSource {

	public BurningSource(Entity entity, int lightlevel) {
		super(entity, lightlevel);
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && getOwner().getFireTicks() > 0;
	}
}
