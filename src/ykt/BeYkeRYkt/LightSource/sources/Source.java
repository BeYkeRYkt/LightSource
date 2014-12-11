package ykt.BeYkeRYkt.LightSource.sources;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;

public abstract class Source {

	public enum ItemType {
		NONE, HELMET, HAND;
	}

	private Entity owner;
	private Location location;
	private LightItem item;
	private ItemType type;
	private ItemStack itemStack = new ItemStack(Material.AIR);

	public Source(Entity entity, Location loc, LightItem item, ItemType type) {
		this.owner = entity;
		this.location = loc;
		this.item = item;
		this.type = type;
	}

	public Source(Entity entity, Location loc, LightItem item, ItemType type, ItemStack itemStack) {
		this.owner = entity;
		this.location = loc;
		this.item = item;
		this.type = type;
		this.itemStack = itemStack;
	}

	/**
	 * @return the owner
	 */
	public Entity getOwner() {
		return owner;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	public ChunkCoords getChunk() {
		return new ChunkCoords(getLocation().getChunk());
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the item
	 */
	public LightItem getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(LightItem item) {
		this.item = item.clone();
	}

	public ItemType getType() {
		return this.type;
	}

	public void updateLight(Location newLocation) {
		LightAPI.deleteLight(getLocation());
		setLocation(newLocation);
		LightAPI.createLight(getLocation(), item.getLevelLight());
		// LightAPI.getSourceManager().addChunk(new
		// ChunkCoords(getLocation().getChunk()));
	}

	public abstract void doTick();

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof Source)) {
			return false;
		} else {
			Source source = (Source) object;
			return getOwner().getEntityId() == source.getOwner().getEntityId(); // ???
		}
	}

	/**
	 * @return the itemStack
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 * @param itemStack
	 *            the itemStack to set
	 */
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public void doBurnTick() {
		if (!getItem().isEnded()) {
			int percent = this.getItem().getPercent();
			int level = this.getItem().getLevelLight();

			Bukkit.broadcastMessage("Percent: " + percent);
			Bukkit.broadcastMessage("level: " + level);
			this.getItem().burn();

			// Maybe playEffect ?
			this.getLocation().getWorld().playEffect(this.getOwner().getLocation(), Effect.SMOKE, 1);
			this.getLocation().getWorld().playEffect(this.getOwner().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

			if (percent >= 80 && percent <= 90) {
				if (level > 13) {
					this.getItem().setLevelLight(13);
				}
			} else if (percent >= 60 && percent < 80) {
				if (level > 11) {
					this.getItem().setLevelLight(11);
				}
			} else if (percent >= 40 && percent < 60) {
				if (level > 9) {
					this.getItem().setLevelLight(9);
				}
			} else if (percent >= 10 && percent < 30) {
				if (level > 7) {
					this.getItem().setLevelLight(7);
				}
			} else if (percent > 1 && percent < 10) {
				if (level > 5) {
					this.getItem().setLevelLight(5);
				}
			} else if (percent >= 0 && percent < 1) {
				// off light...
				this.getItem().setLevelLight(0);
				this.getLocation().getWorld().playSound(this.getLocation(), Sound.FIZZ, 1, 1);
				this.getItem().setEnd();
				LightAPI.deleteLight(this.getLocation());

				// remove item
				if (this.getOwner().getType().isAlive()) {
					LivingEntity le = (LivingEntity) this.getOwner();
					ItemStack item = null;
					if (this.getType() == ItemType.HAND) {
						item = le.getEquipment().getItemInHand();
						if (item.getAmount() > 1) {
							item.setAmount(item.getAmount() - 1);
							LightItem light = ItemManager.getLightItem(item);
							this.setItem(light);
							this.setItemStack(item);
						} else {
							le.getEquipment().setItemInHand(null);
						}
					} else if (this.getType() == ItemType.HELMET) {
						item = le.getEquipment().getHelmet();
						if (item.getAmount() > 1) {
							item.setAmount(item.getAmount() - 1);
							LightItem light = ItemManager.getLightItem(item);
							this.setItem(light);
							this.setItemStack(item);
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
	}
}
