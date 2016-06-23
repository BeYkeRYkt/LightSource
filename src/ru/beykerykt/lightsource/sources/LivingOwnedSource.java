package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.LivingEntity;

import ru.beykerykt.lightsource.items.Item;

public class LivingOwnedSource extends OwnedSource {

	public LivingOwnedSource(LivingEntity entity, Item item) {
		super(entity, item);
	}

	@Override
	public LivingEntity getOwner() {
		return (LivingEntity) super.getOwner();
	}
}
