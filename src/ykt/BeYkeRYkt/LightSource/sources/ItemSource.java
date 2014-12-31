package ykt.BeYkeRYkt.LightSource.sources;

import org.bukkit.Location;
import org.bukkit.entity.Item;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.LightAPI;
import ykt.BeYkeRYkt.LightSource.api.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.api.items.LightItem;
import ykt.BeYkeRYkt.LightSource.api.sources.Source;
import ykt.BeYkeRYkt.LightSource.nbt.comphenix.AttributeStorage;

public class ItemSource extends Source {

    public ItemSource(Item entity, LightItem item) {
        super(entity, entity.getLocation(), item, ItemType.ITEM, entity.getItemStack());
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
        AttributeStorage storage = AttributeStorage.newTarget(getItemStack(), ItemManager.TIME_ID);
        storage.setData(String.valueOf(getBurnTime()));
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