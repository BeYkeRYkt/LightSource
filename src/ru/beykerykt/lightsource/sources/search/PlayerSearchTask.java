package ru.beykerykt.lightsource.sources.search;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.ItemSlot;
import ru.beykerykt.lightsource.sources.InventorySlotSource;
import ru.beykerykt.lightsource.sources.Source;

public class PlayerSearchTask implements SearchTask {

	@Override
	public void onTick() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (LightSourceAPI.getSourceManager().isSource(player))
				continue;

			// all armor set
			if (player.getEquipment().getArmorContents().length == 0)
				continue;
			for (int i = 0; i < player.getEquipment().getArmorContents().length; i++) {
				ItemStack itemStack = player.getEquipment().getArmorContents()[i];
				if (itemStack == null || itemStack.getType() == Material.AIR)
					continue;
				if (!LightSourceAPI.getItemManager().isItem(itemStack))
					continue;
				Item item = LightSourceAPI.getItemManager().getItemFromItemStack(itemStack);
				if (item.getFlagsList().isEmpty())
					continue;
				ItemSlot slot = ItemSlot.getItemSlotFromArmorContent(i);
				if (LightSourceAPI.getSearchMachine().callRequirementFlags(player, itemStack, item, slot)) {
					Source source = new InventorySlotSource(player, item, slot);
					LightSourceAPI.getSourceManager().addSource(source);
				}
			}

			// Main item hand
			ItemStack itemStack = player.getEquipment().getItemInMainHand();
			if (itemStack == null || itemStack.getType() == Material.AIR)
				continue;
			if (!LightSourceAPI.getItemManager().isItem(itemStack))
				continue;
			Item item = LightSourceAPI.getItemManager().getItemFromItemStack(itemStack);
			if (item.getFlagsList().isEmpty())
				continue;
			if (LightSourceAPI.getSearchMachine().callRequirementFlags(player, itemStack, item, ItemSlot.RIGHT_HAND)) {
				Source source = new InventorySlotSource(player, item, ItemSlot.RIGHT_HAND);
				LightSourceAPI.getSourceManager().addSource(source);
			}

			// Off item hand
			ItemStack itemStackOff = player.getEquipment().getItemInOffHand();
			if (itemStackOff == null || itemStackOff.getType() == Material.AIR) {
				continue;
			}
			if (!LightSourceAPI.getItemManager().isItem(itemStackOff))
				continue;
			Item itemOff = LightSourceAPI.getItemManager().getItemFromItemStack(itemStackOff);
			if (itemOff.getFlagsList().isEmpty())
				continue;
			if (LightSourceAPI.getSearchMachine().callRequirementFlags(player, itemStackOff, itemOff, ItemSlot.LEFT_HAND)) {
				Source source = new InventorySlotSource(player, itemOff, ItemSlot.LEFT_HAND);
				LightSourceAPI.getSourceManager().addSource(source);
			}
		}
	}

}
