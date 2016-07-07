package ru.beykerykt.lightsource.sources;

import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;

public class EntityItemSource extends OwnedSource {

	public EntityItemSource(org.bukkit.entity.Item entity, Item item) {
		super(entity, item);
	}

	@Override
	public org.bukkit.entity.Item getOwner() {
		return (org.bukkit.entity.Item) super.getOwner();
	}

	public ItemStack getItemStack() {
		return getOwner().getItemStack();
	}

	@Override
	public boolean shouldExecute() {
		return super.shouldExecute() && LightSourceAPI.getItemManager().isItem(getItemStack());
	}
}
