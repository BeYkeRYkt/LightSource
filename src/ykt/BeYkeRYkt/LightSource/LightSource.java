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

import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.sources.Source;

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
                fc.addDefault("LightUpdateMode", "SAVE");

                fc.addDefault("PlayerLight", true);
                fc.addDefault("EntityLight", false);
                fc.addDefault("ItemLight", false);
                fc.addDefault("LightSourceDamage", true);

                fc.addDefault("Task-delay-ticks", 5);
                fc.addDefault("max-iterations-per-tick", 3);
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

        Bukkit.getPluginManager().registerEvents(new LightListener(), this);

        // mcstats
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
    }

    @Override
    public void onDisable() {
        getAPI().getNMSHandler().unloadWorlds();
        Bukkit.getScheduler().cancelTasks(this);
        ItemManager.getList().clear();
        HandlerList.unregisterAll(this);
        int index;
        for (index = LightAPI.getSourceManager().getSourceList().size() - 1; index >= 0; --index) {
            Source light = LightAPI.getSourceManager().getSourceList().get(index);
            LightAPI.deleteLight(light.getLocation());
            LightAPI.getSourceManager().removeSource(light);
        }
        getDB().save();
        api = null;
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
                getAPI().getGUIManager().openMenu(player, getAPI().getGUIManager().getMenuFromId("mainMenu"));
            } else if (cmd.getName().equalsIgnoreCase("light")) {
                if (args.length == 0) {
                    // Menus.openLightCreatorMenu(player);
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("create")) {
                        player.sendMessage(ChatColor.RED + "Need more arguments!");
                        player.sendMessage(ChatColor.RED + "/light create [level 1-15]");
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        // LightAPI.deleteLightSource(player.getLocation());
                        player.sendMessage(ChatColor.GREEN + "Light successfully deleted!");
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")) {
                        int lightlevel = Integer.parseInt(args[1]);
                        if (lightlevel <= 15) {
                            // LightAPI.createLightSource(player.getLocation(),
                            // lightlevel, true);
                            player.sendMessage(ChatColor.GREEN + "Light successfully created!");
                        } else {
                            player.sendMessage(ChatColor.RED + "Maximum 15 level!");
                            player.sendMessage(ChatColor.RED + "/light create [level 1-15]");
                        }
                    } else if (args[0].equalsIgnoreCase("delete")) {
                        // LightAPI.deleteLightSource(player.getLocation());
                        player.sendMessage(ChatColor.GREEN + "Light successfully deleted!");
                    }
                } else {
                    // Menus.openLightCreatorMenu(player);
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