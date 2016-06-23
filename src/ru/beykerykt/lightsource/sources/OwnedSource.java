package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.Entity;

import ru.beykerykt.lightsource.items.Item;

public class OwnedSource extends Source {

	private Entity owner;

	public OwnedSource(Entity entity, Item item) {
		super(entity.getLocation(), item);
		this.owner = entity;
	}

	public Entity getOwner() {
		return owner;
	}

	@Override
	public boolean shouldExecute() {
		return getOwner() != null && !getOwner().isDead();
	}

	@Override
	public void onUpdate() {
		setLocation(getOwner().getLocation());
		getItem().callUpdateFlag(this);
	}

}
