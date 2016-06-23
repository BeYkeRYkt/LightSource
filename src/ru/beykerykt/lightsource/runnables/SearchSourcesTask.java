package ru.beykerykt.lightsource.runnables;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.sources.HoldItemInHandSource;
import ru.beykerykt.lightsource.sources.HoldItemInHandSource.ItemHand;
import ru.beykerykt.lightsource.sources.LivingOwnedSource;
import ru.beykerykt.lightsource.sources.Source;

public class SearchSourcesTask implements Runnable {

	private int iteratorCount = 0;
	private int maxIterationsPerTick;

	public SearchSourcesTask(int maxIterationsPerTicks) {
		this.maxIterationsPerTick = maxIterationsPerTicks;
	}

	@Override
	public void run() {
		// TODO: Implement iterations
		// while (!LightSourceAPI.getSourceManager().getSourceList().isEmpty() && iteratorCount < maxIterationsPerTick) {
		// iteratorCount++;
		// }
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (LightSourceAPI.getSourceManager().getSource(entity) != null)
					continue;
				if (entity.getType().isAlive()) {
					LivingEntity le = (LivingEntity) entity;

					// all armor set
					if (le.getEquipment().getArmorContents().length == 0)
						continue;
					for (ItemStack itemStack : le.getEquipment().getArmorContents()) {
						if (itemStack == null || itemStack.getType() == Material.AIR)
							continue;
						if (!LightSourceAPI.getItemManager().isItem(itemStack))
							continue;
						Item item = LightSourceAPI.getItemManager().getItemFromItemStack(itemStack);
						if (item.getFlagsList().isEmpty())
							continue;
						if (item.callRequirementFlags(le, itemStack)) {
							Source source = new LivingOwnedSource(le, item);
							LightSourceAPI.getSourceManager().addSource(source);
						}
					}

					// Main item hand
					ItemStack itemStack = le.getEquipment().getItemInMainHand();
					if (itemStack == null || itemStack.getType() == Material.AIR)
						continue;
					if (!LightSourceAPI.getItemManager().isItem(itemStack))
						continue;
					Item item = LightSourceAPI.getItemManager().getItemFromItemStack(itemStack);
					if (item.getFlagsList().isEmpty())
						continue;
					if (item.callRequirementFlags(le, itemStack)) {
						Source source = new HoldItemInHandSource(le, item, ItemHand.MAIN);
						LightSourceAPI.getSourceManager().addSource(source);
					}
					
					// Off item hand
					ItemStack itemStackOff = le.getEquipment().getItemInOffHand();
					if (itemStackOff == null || itemStackOff.getType() == Material.AIR)
						continue;
					if (!LightSourceAPI.getItemManager().isItem(itemStackOff))
						continue;
					Item itemOff = LightSourceAPI.getItemManager().getItemFromItemStack(itemStackOff);
					if (itemOff.getFlagsList().isEmpty())
						continue;
					if (itemOff.callRequirementFlags(le, itemStackOff)) {
						Source source = new HoldItemInHandSource(le, itemOff, ItemHand.OFF);
						LightSourceAPI.getSourceManager().addSource(source);
					}
				}
			}
		}
	}

}
