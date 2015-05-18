package ru.BeYkeRYkt.LightSource.sources;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.BeYkeRYkt.LightSource.ChunkCoords;
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

    private int light;

    public Source(Entity entity, Location loc, LightItem item, ItemType type) {
        this.owner = entity;
        this.location = loc;
        this.type = type;

        if (item != null) {
            this.item = item;
            this.light = item.getMaxLevelLight();
        }
    }

    public Source(Entity entity, Location loc, LightItem item, ItemType type, ItemStack itemStack) {
        this(entity, loc, item, type);
        this.itemStack = itemStack;
    }

    public Source(Entity entity, Location loc, ItemType type, int burnTime, int lightlevel) {
        this(entity, loc, null, type);
        this.light = lightlevel;
    }

    public Source(Entity entity, Location loc, ItemStack itemStack, ItemType type, int burnTime, int lightlevel) {
        this(entity, loc, null, type, itemStack);
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
        this.light = item.getMaxLevelLight();
    }

    public ItemType getType() {
        return this.type;
    }

    public void updateLight(Location newLocation) {
        // for save :)
        if (!LightSource.getInstance().getDB().isIgnoreSaveUpdate()) {
            if (newLocation.getBlockX() != getLocation().getBlockX() || newLocation.getBlockY() != getLocation().getBlockY() || newLocation.getBlockZ() != getLocation().getBlockZ()) {
                LightSource.getInstance().getRegistry().deleteLight(getLocation(), false);
                setLocation(newLocation);
                LightSource.getInstance().getRegistry().createLight(getLocation(), getLevelLight(), true);
            }
        } else {
            LightSource.getInstance().getRegistry().deleteLight(getLocation(), false);
            setLocation(newLocation);
            LightSource.getInstance().getRegistry().createLight(getLocation(), getLevelLight(), true);
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

    public int getLevelLight() {
        return light;
    }

    public void setLevelLight(int newLevel) {
        this.light = newLevel;
    }

    // for remove item
    protected abstract void removeItem();
}
