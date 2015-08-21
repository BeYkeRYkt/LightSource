package ru.BeYkeRYkt.LightSource.task.tasks;

import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.items.ItemManager;
import ru.BeYkeRYkt.LightSource.sources.BurnSource;
import ru.BeYkeRYkt.LightSource.sources.EntitySource;
import ru.BeYkeRYkt.LightSource.sources.ItemSource;
import ru.BeYkeRYkt.LightSource.sources.PlayerSource;
import ru.BeYkeRYkt.LightSource.sources.Source.ItemType;
import ru.BeYkeRYkt.LightSource.task.Task;

public class FoundEntityTask extends Task {

	@Override
	public String getId() {
		return "main_FoundEntity";
	}

	@Override
	public void doTick() {
		Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
		for (Player player : onlinePlayers) {
			int radius = 16;

			if (LightSource.getInstance().getDB().isPlayerLight()) {
				if (LightSource.getInstance().getDB().getWorld(player.getWorld().getName())) {
					if (LightSource.getInstance().getSourceManager().getSource(player) == null) {
						if (!player.isDead()) {
							if (player.getFireTicks() > 0) {
								BurnSource source = new BurnSource(player);
								LightSource.getInstance().getSourceManager().addSource(source);
							}

							if (player.getEquipment().getItemInHand() != null && ItemManager.isLightSource(player.getEquipment().getItemInHand())) {
								PlayerSource light = new PlayerSource(player, player.getLocation(), ItemManager.getLightItem(player.getEquipment().getItemInHand()), ItemType.HAND, player.getEquipment().getItemInHand());
								LightSource.getInstance().getSourceManager().addSource(light);
							} else if (player.getEquipment().getHelmet() != null && ItemManager.isLightSource(player.getEquipment().getHelmet())) {
								PlayerSource light = new PlayerSource(player, player.getLocation(), ItemManager.getLightItem(player.getEquipment().getHelmet()), ItemType.HELMET, player.getEquipment().getHelmet());
								LightSource.getInstance().getSourceManager().addSource(light);
							}
						}
					}
				}
			}

			List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
			for (int j = 0; j < nearbyEntities.size(); j++) {
				Entity ent = nearbyEntities.get(j);

				if (LightSource.getInstance().getDB().isEntityLight()) {
					if (LightSource.getInstance().getDB().getWorld(ent.getWorld().getName())) {
						if (ent.getType().isAlive() && ent.getType() != EntityType.PLAYER) {
							LivingEntity le = (LivingEntity) ent;

							if (LightSource.getInstance().getSourceManager().getSource(le) == null) {
								if (!le.isDead()) {
									if (le.getFireTicks() > 0) {
										BurnSource source = new BurnSource(le);
										LightSource.getInstance().getSourceManager().addSource(source);
									}

									if (le.getEquipment().getItemInHand() != null && ItemManager.isLightSource(le.getEquipment().getItemInHand())) {
										EntitySource light = new EntitySource(le, le.getLocation(), ItemManager.getLightItem(le.getEquipment().getItemInHand()), ItemType.HAND, le.getEquipment().getItemInHand());
										LightSource.getInstance().getSourceManager().addSource(light);

									} else if (le.getEquipment().getHelmet() != null && ItemManager.isLightSource(le.getEquipment().getHelmet())) {
										EntitySource light = new EntitySource(le, le.getLocation(), ItemManager.getLightItem(le.getEquipment().getHelmet()), ItemType.HELMET, le.getEquipment().getHelmet());
										LightSource.getInstance().getSourceManager().addSource(light);

									}
								}
							}
						}
					}
				}

				if (LightSource.getInstance().getDB().isItemLight()) {
					if (LightSource.getInstance().getDB().getWorld(ent.getWorld().getName())) {
						if (ent.getType() == EntityType.DROPPED_ITEM) {
							Item item = (Item) ent;
							ItemStack stack = item.getItemStack();
							if (item.getFireTicks() > 0) {
								BurnSource source = new BurnSource(item);
								LightSource.getInstance().getSourceManager().addSource(source);
							}

							if (LightSource.getInstance().getSourceManager().getSource(item) == null) {
								if (ItemManager.isLightSource(stack)) {
									ItemSource light = new ItemSource(item, ItemManager.getLightItem(stack));
									LightSource.getInstance().getSourceManager().addSource(light);
								}
							}
						}
					}
				}
			}
		}
	}

}
