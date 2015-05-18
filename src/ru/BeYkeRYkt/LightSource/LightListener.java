package ru.BeYkeRYkt.LightSource;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ru.BeYkeRYkt.LightSource.gui.Icon;
import ru.BeYkeRYkt.LightSource.gui.Menu;
import ru.BeYkeRYkt.LightSource.gui.editor.PlayerCreator;
import ru.BeYkeRYkt.LightSource.gui.editor.PlayerEditor;
import ru.BeYkeRYkt.LightSource.items.ItemManager;
import ru.BeYkeRYkt.LightSource.items.LightItem;
import ru.BeYkeRYkt.LightSource.sources.ItemSource;
import ru.BeYkeRYkt.LightSource.sources.PlayerSource;
import ru.BeYkeRYkt.LightSource.sources.Source;
import ru.BeYkeRYkt.LightSource.sources.Source.ItemType;
import de.albionco.updater.Response;
import de.albionco.updater.Updater;
import de.albionco.updater.Version;

public class LightListener implements Listener {

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().getName().equals("LightAPI")) {
            event.getPlugin().getServer().getPluginManager().disablePlugin(LightSource.getInstance());
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled())
            return;
        Player player = event.getPlayer();
        if (LightSource.getInstance().getEditorManager().isEditor(player.getName())) {
            PlayerEditor editor = LightSource.getInstance().getEditorManager().getEditor(player.getName());
            event.setCancelled(true);

            if (editor.getStage() == 1) {// Change name
                String message = event.getMessage();

                if (message.equalsIgnoreCase("null") || message.equalsIgnoreCase("no")) {
                    message = null;
                }
                editor.getItem().setName(message);
                LightSource.getInstance().log(player, "Name changed to " + ChatColor.AQUA + message);
            }
            if (editor.getStage() == 2) {// Change light level
                String message = event.getMessage();

                try {
                    int level = Integer.parseInt(message);
                    if (level > 15) { // User, You are a crazy ?!
                        level = 15;
                    }
                    editor.getItem().setMaxLevelLight(level);
                    LightSource.getInstance().log(player, "Light level changed to " + ChatColor.AQUA + level + ChatColor.WHITE + " / 15.");
                } catch (Exception e) {
                    LightSource.getInstance().log(player, ChatColor.RED + "Please enter numbers");
                    return;
                }
            } else if (editor.getStage() == 3) { // Change data value
                String message = event.getMessage();
                try {
                    int data = Integer.parseInt(message);
                    editor.getItem().setData(data);
                    LightSource.getInstance().log(player, "Data changed to " + ChatColor.AQUA + data + ChatColor.WHITE + ".");
                } catch (Exception e) {
                    LightSource.getInstance().log(player, ChatColor.RED + "Please enter numbers");
                    return;
                }
            }

            Menu menu = LightSource.getInstance().getGUIManager().getMenuFromId("editorMenu");
            LightSource.getInstance().getGUIManager().openMenu(player, menu);
            editor.setStage(0);
            return;
        }

        if (LightSource.getInstance().getEditorManager().isCreator(player.getName())) {
            PlayerCreator creator = LightSource.getInstance().getEditorManager().getCreator(player.getName());
            event.setCancelled(true);

            if (creator.getStage() == 0) {// create item ID
                String id = event.getMessage();
                if (ItemManager.getLightItem(id) != null) {
                    LightSource.getInstance().log(player, ChatColor.RED + "This ID is exists. try new id.");
                    return;
                } else {
                    creator.setID(id);
                    LightSource.getInstance().log(player, "Enter item material (You can see BukkitAPI documentation or use Essentials comamnd /dura).");
                    creator.setStage(1);
                    return;
                }
            } else if (creator.getStage() == 1) {// create Material
                String material = event.getMessage().toUpperCase();
                if (Material.getMaterial(material) != null) {
                    creator.setMaterial(Material.getMaterial(material));
                    creator.setStage(0);

                    LightItem item = new LightItem(creator.getID(), null, Material.getMaterial(material), 0, 15); // Maybe
                                                                                                                  // TODO
                                                                                                                  // FIX
                    ItemManager.addLightSource(item, item.getId());

                    PlayerEditor editor = new PlayerEditor(player.getName(), item);
                    LightSource.getInstance().getEditorManager().removeCreator(creator);
                    LightSource.getInstance().getEditorManager().addEditor(editor);

                    LightSource.getInstance().log(player, ChatColor.DARK_AQUA + "Refreshing GUI Manager...");
                    LightSource.getInstance().getGUIManager().refresh();
                } else {
                    LightSource.getInstance().log(player, ChatColor.RED + "Material is not found. Try again :(");
                    return;
                }
            }
            Menu menu = LightSource.getInstance().getGUIManager().getMenuFromId("editorMenu");
            LightSource.getInstance().getGUIManager().openMenu(player, menu);
            return;
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.isCancelled())
            return;
        Inventory inventory = event.getInventory();

        if (inventory.getTitle() == null)
            return;
        if (LightSource.getInstance().getGUIManager().isMenu(inventory.getTitle())) {
            Menu menu = LightSource.getInstance().getGUIManager().getMenuFromName(inventory.getTitle());
            menu.onOpenMenu(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getTitle() == null)
            return;
        if (LightSource.getInstance().getGUIManager().isMenu(inventory.getTitle())) {
            Menu menu = LightSource.getInstance().getGUIManager().getMenuFromName(inventory.getTitle());
            menu.onCloseMenu(event);
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if (event.isCancelled())
            return;
        ItemStack item = event.getCurrentItem();
        Inventory inventory = event.getInventory();

        if (item == null)
            return;
        if (inventory.getTitle() == null)
            return;
        if (LightSource.getInstance().getGUIManager().isMenu(inventory.getTitle())) {
            if (LightSource.getInstance().getGUIManager().isIcon(item)) {
                if (!item.getItemMeta().hasDisplayName())
                    return;
                Icon icon = LightSource.getInstance().getGUIManager().getIconFromName(item.getItemMeta().getDisplayName());
                icon.onItemClick(event);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropLight(PlayerDropItemEvent event) {
        if (event.isCancelled())
            return;
        for (Source light : LightSource.getInstance().getSourceManager().getSourceList()) {
            if (light.getOwner().getType() == EntityType.PLAYER) {
                Player player = (Player) light.getOwner();
                if (player.getName().equals(event.getPlayer())) {
                    if (player.getItemInHand().getAmount() <= 1) {
                        LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), false);
                    }
                    ItemSource itemsource = new ItemSource(event.getItemDrop(), light.getItem());
                    LightSource.getInstance().getSourceManager().addSource(itemsource);
                }
            }
        }
    }

    @EventHandler
    public void onItemHeldChange(PlayerItemHeldEvent event) {
        if (event.isCancelled())
            return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        Location loc = player.getLocation();

        if (LightSource.getInstance().getDB().getWorld(event.getPlayer().getWorld().getName()) && LightSource.getInstance().getDB().isPlayerLight()) {

            if (item != null && ItemManager.isLightSource(item)) {
                LightItem lightItem = ItemManager.getLightItem(item);

                // AttributeStorage storage = AttributeStorage.newTarget(item,
                // ItemManager.TIME_ID);
                // if (storage.getData(null) != null) {
                // int time = Integer.parseInt(storage.getData(null));
                // lightItem.setBurnTime(time, true);
                // }

                if (LightSource.getInstance().getSourceManager().getSource(player) == null) {
                    PlayerSource light = new PlayerSource(player, loc, lightItem, ItemType.HAND, item);
                    LightSource.getInstance().getSourceManager().addSource(light);
                } else {
                    Source source = LightSource.getInstance().getSourceManager().getSource(player);
                    // set new item
                    source.setItemStack(item);
                    source.setItem(lightItem);
                }
            } else if (item == null || item != null && !ItemManager.isLightSource(item)) {
                if (LightSource.getInstance().getSourceManager().getSource(player) != null) {
                    Source source = LightSource.getInstance().getSourceManager().getSource(player);
                    if (source.getType() == ItemType.HAND) {
                        LightSource.getInstance().getRegistry().deleteLight(source.getLocation(), true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Source light : LightSource.getInstance().getSourceManager().getSourceList()) {
            if (event.getChunk().getX() == light.getLocation().getChunk().getX() && event.getChunk().getZ() == light.getLocation().getChunk().getZ()) {
                LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), true);
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (event.isCancelled())
            return;
        for (Source light : LightSource.getInstance().getSourceManager().getSourceList()) {
            if (event.getChunk().getX() == light.getLocation().getChunk().getX() && event.getChunk().getZ() == light.getLocation().getChunk().getZ()) {
                LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), false);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (LightSource.getInstance().getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightSource.getInstance().getSourceManager().getSource(event.getPlayer());
            LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), true);
            LightSource.getInstance().getSourceManager().removeSource(light);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.isCancelled())
            return;
        if (LightSource.getInstance().getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightSource.getInstance().getSourceManager().getSource(event.getPlayer());
            LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), true);
            LightSource.getInstance().getSourceManager().removeSource(light);
        }
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.isCancelled())
            return;
        if (LightSource.getInstance().getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightSource.getInstance().getSourceManager().getSource(event.getPlayer());
            LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (LightSource.getInstance().getSourceManager().getSource(event.getEntity()) != null) {
            Source light = LightSource.getInstance().getSourceManager().getSource(event.getEntity());
            LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), true);
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled())
            return;
        if (LightSource.getInstance().getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightSource.getInstance().getSourceManager().getSource(event.getPlayer());
            LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), true);
        }
    }

    @EventHandler
    public void onPlayerChangeWorlds(PlayerChangedWorldEvent event) {
        if (LightSource.getInstance().getSourceManager().getSource(event.getPlayer()) != null) {
            Source light = LightSource.getInstance().getSourceManager().getSource(event.getPlayer());
            LightSource.getInstance().getRegistry().deleteLight(light.getLocation(), true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled())
            return;
        if (LightSource.getInstance().getDB().isLightSourceDamage()) {
            if (event.getEntity().getType().isAlive()) {
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (player.isOp() || player.hasPermission("ls.admin")) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(LightSource.getInstance(), new Runnable() {

                @Override
                public void run() {
                    Version version = Version.parse(LightSource.getInstance().getDescription().getVersion());
                    String repo = "BeYkeRYkt/LightSource";

                    Updater updater;
                    try {
                        updater = new Updater(version, repo);

                        Response response = updater.getResult();
                        if (response == Response.SUCCESS) {
                            LightSource.getInstance().log(player, ChatColor.GREEN + "New update is available: " + ChatColor.YELLOW + updater.getLatestVersion() + ChatColor.GREEN + "!");
                            LightSource.getInstance().log(player, ChatColor.GREEN + "Changes: ");
                            player.sendMessage(updater.getChanges());// for
                                                                     // normal
                                                                     // view
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 20);
        }
    }
}