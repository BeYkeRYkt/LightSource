package ru.BeYkeRYkt.LightSource.sources;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.BeYkeRYkt.LightSource.LightAPI;
import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.items.LightItem;

public abstract class Source {

    public enum ItemType {
        NONE, HELMET, HAND, ITEM, BURN;
    }

    private Entity owner;
    private Location location;
    private LightItem item;
    private ItemType type;
    private ItemStack itemStack = new ItemStack(Material.AIR);

    private int burnTime;
    private int light;
    private int ticks;
    private int maxTicks;

    public Source(Entity entity, Location loc, LightItem item, ItemType type) {
        this.maxTicks = 20 / LightSource.getInstance().getDB().getTaskTicks();

        this.owner = entity;
        this.location = loc;
        this.type = type;

        if (item != null) {
            this.item = item;
            this.burnTime = item.getMaxBurnTime();
            this.light = item.getMaxLevelLight();
        }
    }

    public Source(Entity entity, Location loc, LightItem item, ItemType type, ItemStack itemStack) {
        this(entity, loc, item, type);
        this.itemStack = itemStack;
    }

    public Source(Entity entity, Location loc, ItemType type, int burnTime, int lightlevel) {
        this(entity, loc, null, type);
        this.burnTime = burnTime;
        this.light = lightlevel;
    }

    public Source(Entity entity, Location loc, ItemStack itemStack, ItemType type, int burnTime, int lightlevel) {
        this(entity, loc, null, type, itemStack);
        this.burnTime = burnTime;
        this.light = lightlevel;
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
        this.item = item;
        this.burnTime = item.getMaxBurnTime();
        this.light = item.getMaxLevelLight();
    }

    public ItemType getType() {
        return this.type;
    }

    public void updateLight(Location newLocation) {
        // for save :)
        if (!LightSource.getInstance().getDB().isIgnoreSaveUpdate()) {
            if (newLocation.getBlockX() != getLocation().getBlockX() || newLocation.getBlockY() != getLocation().getBlockY() || newLocation.getBlockZ() != getLocation().getBlockZ()) {
                LightAPI.deleteLight(getLocation(), false);
                setLocation(newLocation);
                LightAPI.createLight(getLocation(), getLevelLight());
            }
        } else {
            LightAPI.deleteLight(getLocation(), false);
            setLocation(newLocation);
            LightAPI.createLight(getLocation(), getLevelLight());
        }
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
        if (getItem() == null)
            return;
        if (!isEnded()) {
            int percent = getPercent();
            int level = getLevelLight();
            this.getItem().check();

            // Maybe playEffect ?
            // this.getLocation().getWorld().playEffect(this.getOwner().getLocation(),
            // Effect.SMOKE, 1);
            // this.getLocation().getWorld().playEffect(this.getOwner().getLocation(),
            // Effect.MOBSPAWNER_FLAMES, 1);
            // No.

            if (burnTime > 0) {
                ++ticks;
                if (ticks == maxTicks) {
                    --burnTime;
                    ticks = 0; // restart
                }
            }

            if (percent >= 80 && percent <= 90) {
                if (level > 13) {
                    setLevelLight(13);
                }
            } else if (percent >= 60 && percent < 80) {
                if (level > 11) {
                    setLevelLight(11);
                }
            } else if (percent >= 40 && percent < 60) {
                if (level > 9) {
                    setLevelLight(9);
                }
            } else if (percent >= 10 && percent < 30) {
                if (level > 7) {
                    setLevelLight(7);
                }
            } else if (percent > 1 && percent < 10) {
                if (level > 5) {
                    setLevelLight(5);
                }
            } else if (percent >= 0 && percent < 1) {
                // off light...
                setLevelLight(0);
                this.getLocation().getWorld().playSound(this.getLocation(), Sound.FIZZ, 1, 1);
                setEnd();
                LightAPI.deleteLight(this.getLocation(), false);
                removeItem();
            }
        }
    }

    public int getLevelLight() {
        return light;
    }

    public void setLevelLight(int newLevel) {
        this.light = newLevel;
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    public void setBurnTime(int time, boolean updateLightLevel) {
        this.burnTime = time;

        if (updateLightLevel) {
            if (getPercent() >= 80 && getPercent() <= 90) {
                if (getLevelLight() > 13) {
                    setLevelLight(13);
                }
            } else if (getPercent() >= 60 && getPercent() < 80) {
                if (getLevelLight() > 11) {
                    setLevelLight(11);
                }
            } else if (getPercent() >= 40 && getPercent() < 60) {
                if (getLevelLight() > 9) {
                    setLevelLight(9);
                }
            } else if (getPercent() >= 10 && getPercent() < 30) {
                if (getLevelLight() > 7) {
                    setLevelLight(7);
                }
            } else if (getPercent() > 1 && getPercent() < 10) {
                if (getLevelLight() > 5) {
                    setLevelLight(5);
                }
            } else if (getPercent() >= 0 && getPercent() < 1) {
                setLevelLight(0);
            }
        }
    }

    public void setEnd() {
        burnTime = -2;
    }

    public boolean isEnded() {
        return burnTime == -2;
    }

    public void setInfinity() {
        burnTime = -1;
    }

    public boolean isInfinity() {
        return burnTime == -1;
    }

    public int getPercent() {
        return (this.burnTime * 100) / item.getMaxBurnTime();
    }

    // for remove item
    protected abstract void removeItem();
}
