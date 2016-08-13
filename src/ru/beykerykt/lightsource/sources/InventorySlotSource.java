package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.ItemSlot;
import ru.beykerykt.lightsource.items.flags.FlagHelper;

public class InventorySlotSource extends LivingOwnedSource {

	private ItemSlot slot;

	public InventorySlotSource(LivingEntity entity, Item item, ItemSlot slot) {
		super(entity, item);
		this.slot = slot;
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && LightSourceAPI.getItemManager().isItem(getItemStack()) && FlagHelper.callRequirementFlags(getOwner(), getItemStack(), getItem(), true);
	}

	public ItemSlot getItemSlot() {
		return slot;
	}

	public ItemStack getItemStack() {
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
