package ru.beykerykt.lightsource.sources;

import org.bukkit.Location;

import ru.beykerykt.lightsource.items.Item;

public abstract class Source {

	private Item item;
	private Location old;
	private Location loc;

	public Source(Location location, Item item) {
		this.old = location;
		this.loc = location;
		setItem(item);
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public abstract boolean shouldExecute();

	public abstract void onUpdate();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
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
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		} else if (!item.equals(other.item)) {
			return false;
		}
		if (getLocation() == null) {
			if (other.getLocation() != null) {
				return false;
			}
		} else if (!getLocation().equals(other.getLocation())) {
			return false;
		}
		return true;
	}

}
