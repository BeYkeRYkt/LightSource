package ykt.BeYkeRYkt.LightSource.sources;

import org.bukkit.Location;
import org.bukkit.entity.Item;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;
import ykt.BeYkeRYkt.LightSource.nbt.comphenix.AttributeStorage;

public class ItemSource extends Source {

    public ItemSource(Item entity, Location loc, LightItem item, ItemType type) {
        super(entity, entity.getLocation(), item, type, entity.getItemStack());
    }

    @Override
    public void doTick() {
        Item entity = (Item) getOwner();
        Location newLocation = entity.getLocation();

        if (LightSource.getInstance().getDB().isItemLight()) {
            if (LightSource.getInstance().getDB().getWorld(this.getOwner().getWorld().getName())) {
                if (!entity.isDead()) {
                    if (entity.getItemStack() != null && ItemManager.isLightSource(entity.getItemStack())) {
                        updateLight(newLocation);
                        return;
                    }
                }
            }
        }
        LightAPI.deleteLight(this.getLocation(), true);
        AttributeStorage storage = AttributeStorage.newTarget(getItemStack(), ItemManager.ITEM_ID);
        storage.setData(String.valueOf(getItem().getBurnTime()));
        LightAPI.getSourceManager().removeSource(this);
        return;
    }

    @Override
    protected void removeItem() {
        // remove item
        Item item = (Item) getOwner();
        item.remove();
        // End
    }
}