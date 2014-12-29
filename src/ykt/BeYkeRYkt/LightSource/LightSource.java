package ykt.BeYkeRYkt.LightSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ykt.BeYkeRYkt.LightSource.api.LightAPI;
import ykt.BeYkeRYkt.LightSource.api.gui.Menu;
import ykt.BeYkeRYkt.LightSource.api.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.api.sources.ChunkCoords;
import ykt.BeYkeRYkt.LightSource.api.sources.Source;

public class LightSource extends JavaPlugin {

    private static LightSource plugin;
    private static LightAPI api;
    private LSConfig db;

    @Override
    public void onEnable() {
        LightSource.plugin = this;
        LightSource.api = new LightAPI();
        PluginDescriptionFile pdfFile = getDescription();
        try {
            FileConfiguration fc = getConfig();
            if (!new File(getDataFolder(), "config.yml").exists()) {
                fc.options().header("LightSource v" + pdfFile.getVersion() + " Configuration" + "\nHave fun :3" + "\nby BeYkeRYkt" + "\nUpdate modes can be: SAVE, MAXIMUM and USER");
                fc.addDefault("LightUpdateMode", "USER");

                fc.addDefault("PlayerLight", true);
                fc.addDefault("EntityLight", false);
                fc.addDefault("ItemLight", false);
                fc.addDefault("BurnLight", false);
                fc.addDefault("LightSourceDamage", true);
                fc.addDefault("Ignore-save-update-light", false);

                fc.addDefault("Task-delay-ticks", 10);
                fc.addDefault("max-iterations-per-tick", 10);
                fc.addDefault("Damage-fire-ticks-sec", 5);

                List<World> worlds = getServer().getWorlds();
                for (World world : worlds) {
                    fc.addDefault("Worlds." + world.getName(), true);
                }
                fc.options().copyDefaults(true);
                saveConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.db = new LSConfig(this);
        createExampleItems();
        getAPI().init();

        if (getAPI().getNMSHandler() != null) {
            Bukkit.getPluginManager().registerEvents(new LightListener(), this);

            // mcstats
            try {
                Metrics metrics = new Metrics(this);
                metrics.start();
            } catch (IOException e) {
                // Failed to submit the stats :-(
            }
        }
    }

    @Override
    public void onDisable() {
        getAPI().getNMSHandler().unloadWorlds();
        Bukkit.getScheduler().cancelTasks(this);
        LightAPI.getEditorManager().save();
        ItemManager.getList().clear();
        HandlerList.unregisterAll(this);
        int index;
        for (index = LightAPI.getSourceManager().getSourceList().size() - 1; index >= 0; --index) {
            Source light = LightAPI.getSourceManager().getSourceList().get(index);
            LightAPI.deleteLight(light.getLocation(), true);
            LightAPI.getSourceManager().removeSource(light);
        }
        getDB().save();
        api = null;
        db = null;
    }

    public static LightSource getInstance() {
        return plugin;
    }

    public static LightAPI getAPI() {
        return api;
    }

    public LSConfig getDB() {
        return db;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("ls")) {
                if (player.hasPermission("ls.admin") || player.isOp()) {
                    getAPI().getGUIManager().openMenu(player, getAPI().getGUIManager().getMenuFromId("mainMenu"));
                } else {
                    getAPI().log(player, "Nope :)");
                }
            } else if (cmd.getName().equalsIgnoreCase("light")) {
                if (player.hasPermission("ls.admin") || player.isOp()) {
                    if (args.length == 0) {
                        Menu menu = getAPI().getGUIManager().getMenuFromId("lc_mainMenu");
                        getAPI().getGUIManager().openMenu(player, menu);
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("create")) {
                            getAPI().log(player, ChatColor.RED + "Need more arguments!");
                            getAPI().log(player, ChatColor.RED + "/light create [level 1-15]");
                        } else if (args[0].equalsIgnoreCase("delete")) {
                            LightAPI.deleteLight(player.getLocation(), true);
                            getAPI().log(player, ChatColor.GREEN + "Light successfully deleted!");
                        }
                    } else if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("create")) {
                            int lightlevel = Integer.parseInt(args[1]);
                            if (lightlevel <= 15) {
                                LightAPI.createLight(player.getLocation(), lightlevel);
                                LightAPI.updateChunk(new ChunkCoords(player.getLocation().getChunk()));
                                player.getLocation().getChunk().unload(true);
                                player.getLocation().getChunk().load(true);
                                getAPI().log(player, ChatColor.GREEN + "Light successfully created!");
                            } else {
                                getAPI().log(player, ChatColor.RED + "Maximum 15 level!");
                                getAPI().log(player, ChatColor.RED + "/light create [level 1-15]");
                            }
                        } else if (args[0].equalsIgnoreCase("delete")) {
                            LightAPI.deleteLight(player.getLocation(), true);
                            getAPI().log(player, ChatColor.GREEN + "Light successfully deleted!");
                        }
                    } else {
                        Menu menu = getAPI().getGUIManager().getMenuFromId("lc_mainMenu");
                        getAPI().getGUIManager().openMenu(player, menu);
                    }
                } else {
                    getAPI().log(player, "Nope :)");
                }
            }
        }
        return true;
    }

    public void createExampleItems() {
        try {
            getAPI();
            FileConfiguration fc = LightAPI.getItemManager().getConfig();

            if (!new File(getDataFolder(), "Items.yml").exists()) {

                fc.addDefault("Lava.material", "LAVA");
                fc.addDefault("Lava.lightlevel", 15);
                fc.addDefault("Lava.burnTime", -1);

                fc.addDefault("StationLava.material", "STATIONARY_LAVA");
                fc.addDefault("StationLava.lightlevel", 15);
                fc.addDefault("StationLava.burnTime", -1);

                fc.addDefault("Fire.material", "FIRE");
                fc.addDefault("Fire.lightlevel", 115);
                fc.addDefault("Fire.burnTime", -1);

                fc.addDefault("Jack.material", "JACK_O_LANTERN");
                fc.addDefault("Jack.lightlevel", 15);
                fc.addDefault("Jack.burnTime", 60);

                fc.addDefault("LavaBucket.material", "LAVA_BUCKET");
                fc.addDefault("LavaBucket.lightlevel", 15);
                fc.addDefault("LavaBucket.burnTime", 60);

                fc.addDefault("Torch.material", "TORCH");
                fc.addDefault("Torch.lightlevel", 14);
                fc.addDefault("Torch.burnTime", 60);

                fc.addDefault("Glowstone.material", "GLOWSTONE");
                fc.addDefault("Glowstone.lightlevel", 14);
                fc.addDefault("Glowstone.burnTime", -1);

                fc.addDefault("BlazeRod.material", "BLAZE_ROD");
                fc.addDefault("BlazeRod.lightlevel", 5);
                fc.addDefault("BlazeRod.burnTime", 30);

                fc.addDefault("Redstone.material", "REDSTONE_TORCH_ON");
                fc.addDefault("Redstone.lightlevel", 9);
                fc.addDefault("Redstone.burnTime", 20);

                fc.addDefault("SeaLatern.material", "SEA_LANTERN");
                fc.addDefault("SeaLatern.lightlevel", 15);
                fc.addDefault("SeaLatern.burnTime", -1);

                fc.addDefault("RedstoneLamp.material", "REDSTONE_LAMP_ON");
                fc.addDefault("RedstoneLamp.lightlevel", 15);
                fc.addDefault("RedstoneLamp.burnTime", -1);

                fc.addDefault("Furnace.material", "BURNING_FURNACE");
                fc.addDefault("Furnace.lightlevel", 13);
                fc.addDefault("Furnace.burnTime", -1);

                fc.addDefault("RedstoneOre.material", "REDSTONE_ORE");
                fc.addDefault("RedstoneOre.lightlevel", 9);
                fc.addDefault("RedstoneOre.burnTime", -1);

                fc.addDefault("EnderChest.material", "ENDER_CHEST");
                fc.addDefault("EnderChest.lightlevel", 6);
                fc.addDefault("EnderChest.burnTime", -1);

                fc.options().copyDefaults(true);
                getAPI();
                LightAPI.getItemManager().saveConfig();
                fc.options().copyDefaults(false);
                getAPI();
                LightAPI.getItemManager().reloadConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}