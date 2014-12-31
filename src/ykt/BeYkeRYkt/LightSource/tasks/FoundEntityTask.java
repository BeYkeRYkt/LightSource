package ykt.BeYkeRYkt.LightSource.tasks;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.LightAPI;
import ykt.BeYkeRYkt.LightSource.api.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.api.sources.Source.ItemType;
import ykt.BeYkeRYkt.LightSource.api.task.Task;
import ykt.BeYkeRYkt.LightSource.nbt.comphenix.AttributeStorage;
import ykt.BeYkeRYkt.LightSource.sources.BurnSource;
import ykt.BeYkeRYkt.LightSource.sources.EntitySource;
import ykt.BeYkeRYkt.LightSource.sources.ItemSource;
import ykt.BeYkeRYkt.LightSource.sources.PlayerSource;

public class FoundEntityTask extends Task {

    @Override
    public String getId() {
        return "main_FoundEntity";
    }

    @Override
    public void doTick() {
        @SuppressWarnings("deprecation")
        Player[] onlinePlayers = Bukkit.getOnlinePlayers();
        for (int i = 0; i < onlinePlayers.length; i++) {
            Player player = onlinePlayers[i];

            int radius = 16;

            if (LightSource.getInstance().getDB().isPlayerLight()) {
                if (LightSource.getInstance().getDB().getWorld(player.getWorld().getName())) {
                    if (LightAPI.getSourceManager().getSource(player) == null) {
                        if (!player.isDead()) {
                            if (player.getFireTicks() > 0) {
                                BurnSource source = new BurnSource(player);
                                LightAPI.getSourceManager().addSource(source);
                            }

                            if (player.getEquipment().getItemInHand() != null && ItemManager.isLightSource(player.getEquipment().getItemInHand())) {
                                PlayerSource light = new PlayerSource(player, player.getLocation(), ItemManager.getLightItem(player.getEquipment().getItemInHand()), ItemType.HAND, player.getEquipment().getItemInHand());

                                // restore from pickup
                                AttributeStorage storage = AttributeStorage.newTarget(light.getItemStack(), ItemManager.TIME_ID);
                                if (storage.getData(null) != null) {
                                    int time = Integer.parseInt(storage.getData(null));
                                    light.setBurnTime(time, true);
                                }

                                LightAPI.getSourceManager().addSource(light);
                            } else if (player.getEquipment().getHelmet() != null && ItemManager.isLightSource(player.getEquipment().getHelmet())) {
                                PlayerSource light = new PlayerSource(player, player.getLocation(), ItemManager.getLightItem(player.getEquipment().getHelmet()), ItemType.HELMET, player.getEquipment().getHelmet());

                                // restore from pickup
                                AttributeStorage storage = AttributeStorage.newTarget(light.getItemStack(), ItemManager.TIME_ID);
                                if (storage.getData(null) != null) {
                                    int time = Integer.parseInt(storage.getData(null));
                                    light.setBurnTime(time, true);
                                }
                                LightAPI.getSourceManager().addSource(light);
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

                            if (LightAPI.getSourceManager().getSource(le) == null) {
                                if (!le.isDead()) {
                                    if (le.getFireTicks() > 0) {
                                        BurnSource source = new BurnSource(le);
                                        LightAPI.getSourceManager().addSource(source);
                                    }

                                    if (le.getEquipment().getItemInHand() != null && ItemManager.isLightSource(le.getEquipment().getItemInHand())) {
                                        EntitySource light = new EntitySource(le, le.getLocation(), ItemManager.getLightItem(le.getEquipment().getItemInHand()), ItemType.HAND, le.getEquipment().getItemInHand());

                                        // restore from pickup
                                        AttributeStorage storage = AttributeStorage.newTarget(light.getItemStack(), ItemManager.TIME_ID);
                                        if (storage.getData(null) != null) {
                                            int time = Integer.parseInt(storage.getData(null));
                                            light.setBurnTime(time, true);
                                        }
                                        LightAPI.getSourceManager().addSource(light);

                                    } else if (le.getEquipment().getHelmet() != null && ItemManager.isLightSource(le.getEquipment().getHelmet())) {
                                        EntitySource light = new EntitySource(le, le.getLocation(), ItemManager.getLightItem(le.getEquipment().getHelmet()), ItemType.HELMET, le.getEquipment().getHelmet());

                                        // restore from pickup
                                        AttributeStorage storage = AttributeStorage.newTarget(light.getItemStack(), ItemManager.TIME_ID);
                                        if (storage.getData(null) != null) {
                                            int time = Integer.parseInt(storage.getData(null));
                                            light.setBurnTime(time, true);
                                        }
                                        LightAPI.getSourceManager().addSource(light);

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
                                LightAPI.getSourceManager().addSource(source);
                            }

                            if (LightAPI.getSourceManager().getSource(item) == null) {
                                if (ItemManager.isLightSource(stack)) {
                                    ItemSource light = new ItemSource(item, ItemManager.getLightItem(stack));

                                    // restore from pickup
                                    AttributeStorage storage = AttributeStorage.newTarget(light.getItemStack(), ItemManager.TIME_ID);
                                    if (storage.getData(null) != null) {
                                        int time = Integer.parseInt(storage.getData(null));
                                        light.setBurnTime(time, true);
                                    }
                                    LightAPI.getSourceManager().addSource(light);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}