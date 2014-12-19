package ykt.BeYkeRYkt.LightSource;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.gui.Icon;
import ykt.BeYkeRYkt.LightSource.gui.Menu;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;
import ykt.BeYkeRYkt.LightSource.nbt.comphenix.AttributeStorage;
import ykt.BeYkeRYkt.LightSource.sources.PlayerSource;
import ykt.BeYkeRYkt.LightSource.sources.Source;
import ykt.BeYkeRYkt.LightSource.sources.Source.ItemType;

public class LightListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getTitle() == null)
            return;
        if (LightSource.getAPI().getGUIManager().isMenu(inventory.getTitle())) {
            Menu menu = LightSource.getAPI().getGUIManager().getMenuFromName(inventory.getTitle());
            menu.onOpenMenu(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getTitle() == null)
            return;
        if (LightSource.getAPI().getGUIManager().isMenu(inventory.getTitle())) {
            Menu menu = LightSource.getAPI().getGUIManager().getMenuFromName(inventory.getTitle());
            menu.onCloseMenu(event);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        Inventory inventory = event.getInventory();

        if (item == null)
            return;
        if (inventory.getTitle() == null)
            return;
        if (LightSource.getAPI().getGUIManager().isMenu(inventory.getTitle())) {
            if (LightSource.getAPI().getGUIManager().isIcon(item)) {
                if (!item.getItemMeta().hasDisplayName())
                    return;
                Icon icon = LightSource.getAPI().getGUIManager().getIconFromName(item.getItemMeta().getDisplayName());
                icon.onItemClick(event);
            }
            event.setCancelled(true);
        }
    }

    // Port from old version
    @EventHandler
    public void onItemHeldChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        Location loc = player.getLocation();

        if (LightSource.getInstance().getDB().getWorld(event.getPlayer().getWorld().getName()) && LightSource.getInstance().getDB().isPlayerLight()) {

            if (item != null && ItemManager.isLightSource(item)) {
                LightItem lightItem = ItemManager.getLightItem(item);
                AttributeStorage storage = AttributeStorage.newTarget(item, ItemManager.ITEM_ID);
                if (storage.getData(null) != null) {
                    int time = Integer.parseInt(storage.getData(null));
                    lightItem.setBurnTime(time, true);
                }

                PlayerSource light = new PlayerSource(player, loc, lightItem, ItemType.HAND, item);
                LightAPI.getSourceManager().addSource(light);
            } else if (item == null || item != null && ItemManager.isLightSource(item)) {
                if (LightAPI.getSourceManager().getSource(player) != null) {
                    LightAPI.deleteLight(loc);
                }
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Source light : LightAPI.getSourceManager().getSourceList()) {
            if (event.getChunk().getX() == light.getLocation().getChunk().getX() && event.getChunk().getZ() == light.getLocation().getChunk().getZ()) {
                LightAPI.deleteLight(light.getLocation());
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        for (Source light : LightAPI.getSourceManager().getSourceList()) {
            if (event.getChunk().getX() == light.getLocation().getChunk().getX() && event.getChunk().getZ() == light.getLocation().getChunk().getZ()) {
                LightAPI.deleteLight(light.getLocation());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (LightAPI.getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightAPI.getSourceManager().getSource(event.getPlayer());
            LightAPI.deleteLight(light.getLocation());
            LightAPI.getSourceManager().removeSource(light);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (LightAPI.getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightAPI.getSourceManager().getSource(event.getPlayer());
            LightAPI.deleteLight(light.getLocation());
            LightAPI.getSourceManager().removeSource(light);
        }
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (LightAPI.getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightAPI.getSourceManager().getSource(event.getPlayer());
            LightAPI.deleteLight(light.getLocation());
        }
    }

    @EventHandler
    public void onPlayerDropLight(PlayerDropItemEvent event) {
        for (Source light : LightAPI.getSourceManager().getSourceList()) {
            if (light.getOwner().getType() == EntityType.PLAYER) {
                Player player = (Player) light.getOwner();
                if (player.getName().equals(event.getPlayer())) {
                    if (player.getItemInHand().getAmount() <= 1) {
                        LightAPI.deleteLight(light.getLocation());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (LightAPI.getSourceManager().getSource(event.getEntity()) != null) {
            Source light = LightAPI.getSourceManager().getSource(event.getEntity());
            LightAPI.deleteLight(light.getLocation());
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (LightAPI.getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightAPI.getSourceManager().getSource(event.getPlayer());
            LightAPI.deleteLight(light.getLocation());
        }
    }

    @EventHandler
    public void onPlayerChangeWorlds(PlayerChangedWorldEvent event) {
        if (LightAPI.getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightAPI.getSourceManager().getSource(event.getPlayer());
            LightAPI.deleteLight(light.getLocation());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (LightSource.getInstance().getDB().isLightSourceDamage()) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (event.getDamager().getType().isAlive()) {
                LivingEntity damager = (LivingEntity) event.getDamager();
                if (ItemManager.isLightSource(damager.getEquipment().getItemInHand())) {
                    int fire = LightSource.getInstance().getDB().getDamageFire() * 20;
                    entity.setFireTicks(fire);
                }
            }
        }
    }
}