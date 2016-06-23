package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;

public class HoldItemInHandSource extends LivingOwnedSource {

	public HoldItemInHandSource(LivingEntity entity, Item item) {
		super(entity, item);
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && LightSourceAPI.getItemManager().isItem(getItemStack());
	}

	public ItemStack getItemStack() {
		return getOwner().getEquipment().getItemInHand();
	}
}
