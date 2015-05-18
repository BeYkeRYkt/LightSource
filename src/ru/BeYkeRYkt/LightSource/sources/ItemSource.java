package ru.BeYkeRYkt.LightSource.sources;

import org.bukkit.Location;
import org.bukkit.entity.Item;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.items.ItemManager;
import ru.BeYkeRYkt.LightSource.items.LightItem;

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
        LightSource.getInstance().getRegistry().deleteLight(this.getLocation(), true);
        LightSource.getInstance().getSourceManager().removeSource(this);
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