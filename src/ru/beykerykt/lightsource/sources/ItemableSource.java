package ru.beykerykt.lightsource.sources;

import org.bukkit.Location;

import ru.beykerykt.lightsource.items.Item;

public abstract class ItemableSource extends Source {

	private Item item;

	public ItemableSource(Location location, Item item) {
		super(location, item.getLevelLight());
		this.item = item;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((item == null) ? 0 : item.hashCode());
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
		if (!(obj instanceof ItemableSource)) {
			return false;
		}
		ItemableSource other = (ItemableSource) obj;
		if (item == null) {
			if (other.item != null) {
				return false;
			}
		} else if (!item.equals(other.item)) {
			return false;
		}
		return true;
	}
}
