package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;

public class HoldItemInHandSource extends LivingOwnedSource {

	public enum ItemHand {
		MAIN,
		OFF;
	}

	private ItemHand hand;

	public HoldItemInHandSource(LivingEntity entity, Item item, ItemHand hand) {
		super(entity, item);
		this.hand = hand;
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && LightSourceAPI.getItemManager().isItem(getItemStack());
	}

	public ItemHand getItemHand() {
		return hand;
	}

	public ItemStack getItemStack() {
		if (hand == ItemHand.OFF) {
			return getOwner().getEquipment().getItemInOffHand();
		}
		return getOwner().getEquipment().getItemInMainHand();
	}
}
