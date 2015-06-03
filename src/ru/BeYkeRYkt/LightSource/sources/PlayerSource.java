package ru.BeYkeRYkt.LightSource.sources;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ru.BeYkeRYkt.LightAPI.LightAPI;
import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.items.ItemManager;
import ru.BeYkeRYkt.LightSource.items.LightItem;

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
                    }
                }
            }
        }
        LightAPI.deleteLight(this.getLocation());
        LightSource.getInstance().getSourceManager().removeSource(this);
    }

    @Override
    protected void removeItem() {
        // remove item
        Player player = (Player) this.getOwner();
        ItemStack item = null;
        if (this.getType() == ItemType.HAND) {
            item = player.getEquipment().getItemInHand();
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
                LightItem light = ItemManager.getLightItem(item);
                this.setItem(light);
                this.setItemStack(item);
            } else {
                player.getEquipment().setItemInHand(null);
            }
        } else if (this.getType() == ItemType.HELMET) {
            item = player.getEquipment().getHelmet();
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
                LightItem light = ItemManager.getLightItem(item);
                this.setItem(light);
                this.setItemStack(item);
            } else {
                player.getEquipment().setHelmet(null);
            }
        }
        player.updateInventory();
        // End
    }
}