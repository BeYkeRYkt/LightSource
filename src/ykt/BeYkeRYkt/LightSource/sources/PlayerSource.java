package ykt.BeYkeRYkt.LightSource.sources;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.LightUtils;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;

public class PlayerSource extends Source {

	public PlayerSource(Player entity, Location loc, LightItem item, ItemType type, ItemStack itemStack) {
		super(entity, loc, item, type, itemStack);
	}

	@Override
	public void doTick() {
		Player entity = (Player) getOwner();
		Location newLocation = entity.getLocation();

		if (LightSource.getInstance().getDB().isPlayerLight()) {
			if (LightSource.getInstance().getDB().getWorld(this.getOwner().getWorld().getName())) {
				if (!entity.isDead()) {
					if (entity.getEquipment().getItemInHand() != null && getItemStack().equals(entity.getEquipment().getItemInHand()) && ItemManager.isLightSource(entity.getEquipment().getItemInHand())) {
						updateLight(newLocation);
						return;
					} else if (entity.getEquipment().getHelmet() != null && getItemStack().equals(entity.getEquipment().getHelmet()) && ItemManager.isLightSource(entity.getEquipment().getHelmet())) {
						updateLight(newLocation);
						return;
					} else {
						LightAPI.deleteLight(this.getLocation());
						// lore :)
						if (entity.getInventory().contains(getItemStack())) {
							LightUtils.setFuelLore(getItemStack(), getItem().getPercent());
							entity.updateInventory();
						}
						LightAPI.getSourceManager().removeSource(this);
						return;
					}
				} else {
					LightAPI.deleteLight(this.getLocation());
					if (entity.getInventory().contains(getItemStack())) {
						LightUtils.setFuelLore(getItemStack(), getItem().getPercent());
						entity.updateInventory();
					}
					LightAPI.getSourceManager().removeSource(this);
					return;
				}
			} else {
				LightAPI.deleteLight(this.getLocation());
				if (entity.getInventory().contains(getItemStack())) {
					LightUtils.setFuelLore(getItemStack(), getItem().getPercent());
					entity.updateInventory();
				}
				LightAPI.getSourceManager().removeSource(this);
				return;
			}
		} else {
			LightAPI.deleteLight(this.getLocation());
			if (entity.getInventory().contains(getItemStack())) {
				LightUtils.setFuelLore(getItemStack(), getItem().getPercent());
				entity.updateInventory();
			}
			LightAPI.getSourceManager().removeSource(this);
			return;
		}
	}

}