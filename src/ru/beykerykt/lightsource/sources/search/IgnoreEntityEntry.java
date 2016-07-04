package ru.beykerykt.lightsource.sources.search;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.items.ItemSlot;

public class IgnoreEntityEntry {
	private Entity entity;
	private ItemSlot slot;
	private ItemStack itemStack;

	public IgnoreEntityEntry(Entity entity, ItemStack itemStack, ItemSlot slot) {
		this.entity = entity;
		this.slot = slot;
		this.itemStack = itemStack;
	}

	public Entity getEntity() {
		return entity;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public ItemSlot getSlot() {
		return slot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((itemStack == null) ? 0 : itemStack.hashCode());
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
		if (!(obj instanceof IgnoreEntityEntry)) {
			return false;
		}
		IgnoreEntityEntry other = (IgnoreEntityEntry) obj;
		if (entity == null) {
			if (other.entity != null) {
				return false;
			}
		} else if (!entity.equals(other.entity)) {
			return false;
		}
		if (itemStack == null) {
			if (other.itemStack != null) {
				return false;
			}
		} else if (!itemStack.equals(other.itemStack)) {
			return false;
		}
		if (slot != other.slot) {
			return false;
		}
		return true;
	}

}
