package ru.beykerykt.lightsource.sources;

import org.bukkit.Location;

public abstract class Source {

	private Location old;
	private Location loc;
	private int lightLevel;

	public Source(Location location, int lightLevel) {
		this.old = location;
		this.loc = location;
		setLightLevel(lightLevel);
	}

	public Location getLocation() {
		return loc;
	}

	public Location getOldLocation() {
		return old;
	}

	public void setLocation(Location loc) {
		this.old = getLocation();
		this.loc = loc;
	}

	public int getLightLevel() {
		return lightLevel;
	}

	public void setLightLevel(int lightLevel) {
		this.lightLevel = lightLevel;
	}

	public abstract boolean shouldExecute();

	public abstract void onUpdate();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lightLevel;
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		result = prime * result + ((old == null) ? 0 : old.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Source)) {
			return false;
		}
		Source other = (Source) obj;
		if (lightLevel != other.lightLevel) {
			return false;
		}
		if (loc == null) {
			if (other.loc != null) {
				return false;
			}
		} else if (!loc.equals(other.loc)) {
			return false;
		}
		if (old == null) {
			if (other.old != null) {
				return false;
			}
		} else if (!old.equals(other.old)) {
			return false;
		}
		return true;
	}
}
