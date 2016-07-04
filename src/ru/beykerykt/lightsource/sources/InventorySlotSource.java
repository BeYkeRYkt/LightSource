package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.ItemSlot;

public class InventorySlotSource extends LivingOwnedSource {

	private ItemSlot slot;

	public InventorySlotSource(LivingEntity entity, Item item, ItemSlot slot) {
		super(entity, item);
		this.slot = slot;
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && LightSourceAPI.getItemManager().isItem(getItemStack());
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
}
