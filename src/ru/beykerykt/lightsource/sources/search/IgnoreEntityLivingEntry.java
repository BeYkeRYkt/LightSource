package ru.beykerykt.lightsource.sources.search;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.items.ItemSlot;

public class IgnoreEntityLivingEntry extends IgnoreEntityEntry {

	private ItemSlot slot;

	public IgnoreEntityLivingEntry(Entity entity, ItemStack itemStack, ItemSlot slot) {
		super(entity, itemStack);
		this.slot = slot;
	}

	public ItemSlot getSlot() {
		return slot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((slot == null) ? 0 : slot.hashCode());
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
		if (!(obj instanceof IgnoreEntityLivingEntry)) {
			return false;
		}
		IgnoreEntityLivingEntry other = (IgnoreEntityLivingEntry) obj;
		if (slot != other.slot) {
			return false;
		}
		return true;
	}
}
