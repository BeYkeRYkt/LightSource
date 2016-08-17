/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 - 2016
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.ItemSlot;
import ru.beykerykt.lightsource.items.flags.FlagHelper;

public class InventorySlotSource extends LivingOwnedSource {

	private ItemSlot slot;
	private ItemStack itemStack;

	public InventorySlotSource(LivingEntity entity, Item item, ItemStack itemStack, ItemSlot slot) {
		super(entity, item);
		this.slot = slot;
		this.itemStack = itemStack;
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && getItemStack().equals(getItemStackFromItemSlot()) && LightSourceAPI.getItemManager().isItem(getItemStackFromItemSlot()) && FlagHelper.callRequirementFlags(getOwner(), getItemStackFromItemSlot(), getItem(), true);
	}

	public ItemSlot getItemSlot() {
		return slot;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public ItemStack getItemStackFromItemSlot() {
		switch (slot) {
			case HELMET:
				return getOwner().getEquipment().getHelmet();
			case CHESTPLATE:
				return getOwner().getEquipment().getChestplate();
			case LEGGINGS:
				return getOwner().getEquipment().getLeggings();
			case BOOTS:
				return getOwner().getEquipment().getBoots();
			case LEFT_HAND:
				return getOwner().getEquipment().getItemInOffHand();
			default:
				return getOwner().getEquipment().getItemInMainHand();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((slot == null) ? 0 : slot.hashCode());
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
		if (!(obj instanceof InventorySlotSource)) {
			return false;
		}
		InventorySlotSource other = (InventorySlotSource) obj;
		if (slot != other.slot) {
			return false;
		}
		return true;
	}
}
