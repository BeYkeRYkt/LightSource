package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.Entity;

import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.flags.FlagHelper;

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
		// getItem().callUpdateFlag(this);
		FlagHelper.callUpdateFlag(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof OwnedSource)) {
			return false;
		}
		OwnedSource other = (OwnedSource) obj;
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		return true;
	}
}
