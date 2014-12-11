package ykt.BeYkeRYkt.LightSource.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;
import ykt.BeYkeRYkt.LightSource.sources.Source;
import ykt.BeYkeRYkt.LightSource.sources.Source.ItemType;
import ykt.BeYkeRYkt.LightSource.sources.SourceManager;

public class BurnTask implements Runnable {

	private SourceManager manager;
	private List<Source> list;

	public BurnTask(SourceManager manager) {
		this.manager = manager;
		this.list = new ArrayList<Source>(this.manager.getSourceList());
	}

	@Override
	public void run() {
		if (list != null && !list.isEmpty()) {
			Source source = list.get(0);
			if (!source.getItem().isEnded()) {
				float percent = source.getItem().getPercent();
				int level = source.getItem().getLevelLight();

				source.getItem().burn();

				// Maybe playEffect ?
				source.getLocation().getWorld().playEffect(source.getOwner().getLocation(), Effect.SMOKE, 1);
				source.getLocation().getWorld().playEffect(source.getOwner().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

				if (percent >= 80 && percent <= 90) {
					if (level > 13) {
						source.getItem().setLevelLight(13);
					}
				} else if (percent >= 60 && percent < 80) {
					if (level > 11) {
						source.getItem().setLevelLight(11);
					}
				} else if (percent >= 40 && percent < 60) {
					if (level > 9) {
						source.getItem().setLevelLight(9);
					}
				} else if (percent >= 10 && percent < 30) {
					if (level > 7) {
						source.getItem().setLevelLight(7);
					}
				} else if (percent > 1 && percent < 10) {
					if (level > 5) {
						source.getItem().setLevelLight(5);
					}
				} else if (percent >= 0 && percent < 1) {
					// off light...
					source.getItem().setLevelLight(0);
					source.getLocation().getWorld().playSound(source.getLocation(), Sound.FIZZ, 1, 1);
					source.getItem().setEnd();
					LightAPI.deleteLight(source.getLocation());

					// remove item
					if (source.getOwner().getType().isAlive()) {
						LivingEntity le = (LivingEntity) source.getOwner();
						ItemStack item = null;
						if (source.getType() == ItemType.HAND) {
							item = le.getEquipment().getItemInHand();
							if (item.getAmount() > 1) {
								item.setAmount(item.getAmount() - 1);
								LightItem light = ItemManager.getLightItem(item);
								source.setItem(light);
								source.setItemStack(item);
							} else {
								le.getEquipment().setItemInHand(null);
							}
						} else if (source.getType() == ItemType.HELMET) {
							item = le.getEquipment().getHelmet();
							if (item.getAmount() > 1) {
								item.setAmount(item.getAmount() - 1);
								LightItem light = ItemManager.getLightItem(item);
								source.setItem(light);
								source.setItemStack(item);
							} else {
								le.getEquipment().setItemInHand(null);
							}
						}

						if (le.getType() == EntityType.PLAYER) {
							((Player) le).updateInventory();
						}
					}
					// End
				}
			}
			list.remove(0);
		}

		if (list.isEmpty()) {
			list.addAll(this.manager.getSourceList());
		}

	}

}