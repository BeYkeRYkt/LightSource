/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 - 2016
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.beykerykt.lightsource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.chunks.ChunkInfo;
import ru.beykerykt.lightsource.items.flags.FlagExecutor;
import ru.beykerykt.lightsource.items.flags.basic.DeleteLightExecutor;
import ru.beykerykt.lightsource.items.flags.basic.EntityCheckExecutor;
import ru.beykerykt.lightsource.items.flags.basic.PermissionCheckExecutor;
import ru.beykerykt.lightsource.items.flags.basic.PlayEffectExecutor;
import ru.beykerykt.lightsource.items.flags.basic.UpdateExecutor;
import ru.beykerykt.lightsource.items.flags.basic.WorldCheckExecutor;
import ru.beykerykt.lightsource.items.loader.YamlLoader;
import ru.beykerykt.lightsource.sources.UpdateSourcesTask;
import ru.beykerykt.lightsource.sources.search.EntitySearchTask;
import ru.beykerykt.lightsource.sources.search.ItemEntitySearchTask;
import ru.beykerykt.lightsource.sources.search.PlayerSearchTask;
import ru.beykerykt.lightsource.updater.Response;
import ru.beykerykt.lightsource.updater.UpdateType;
import ru.beykerykt.lightsource.updater.Updater;
import ru.beykerykt.lightsource.updater.Version;
import ru.beykerykt.lightsource.utils.BungeeChatHelperClass;
import ru.beykerykt.lightsource.utils.Metrics;

public class LightSource extends JavaPlugin {

	private static LightSource plugin;
	private UpdateSourcesTask updateTask;
	private int configVer = 1;

	@Override
	public void onLoad() {
		// register checkers
		LightSourceAPI.getFlagManager().registerFlag("permission", new PermissionCheckExecutor());
		LightSourceAPI.getFlagManager().registerFlag("entity", new EntityCheckExecutor());
		LightSourceAPI.getFlagManager().registerFlag("world", new WorldCheckExecutor());

		// register tickers
		LightSourceAPI.getFlagManager().registerFlag("update", new UpdateExecutor());
		LightSourceAPI.getFlagManager().registerFlag("play_effect", new PlayEffectExecutor());

		// register post execturos
		LightSourceAPI.getFlagManager().registerFlag("delete_light", new DeleteLightExecutor());
	}

	@SuppressWarnings("static-access")
	@Override
	public void onEnable() {
		this.plugin = this;
		// Config
		try {
			FileConfiguration fc = getConfig();
			File file = new File(getDataFolder(), "config.yml");
			if (file.exists()) {
				if (fc.getInt("version") < configVer) {
					file.delete(); // got a better idea?
					generateConfig(file);
				}
			} else {
				generateConfig(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Default item config
		initDefaultConfig();
		new YamlLoader().loadFromFile(new File(getDataFolder(), "sources.yml"));

		if (getConfig().getBoolean(ConfigPath.SOURCES.SEARCH.SEARCH_PLAYERS)) {
			LightSourceAPI.getSearchMachine().addTask(new PlayerSearchTask());
		}
		if (getConfig().getBoolean(ConfigPath.SOURCES.SEARCH.SEARCH_ENTITIES)) {
			LightSourceAPI.getSearchMachine().addTask(new EntitySearchTask(getConfig().getDouble(ConfigPath.SOURCES.SEARCH.SEARCH_RADIUS)));
		}
		if (getConfig().getBoolean(ConfigPath.SOURCES.SEARCH.SEARCH_ITEMS)) {
			LightSourceAPI.getSearchMachine().addTask(new ItemEntitySearchTask(getConfig().getDouble(ConfigPath.SOURCES.SEARCH.SEARCH_RADIUS)));
		}
		LightSourceAPI.getSearchMachine().start(getConfig().getInt(ConfigPath.SOURCES.SEARCH.SEARCH_DELAY_TICKS));

		this.updateTask = new UpdateSourcesTask();
		updateTask.start(getConfig().getInt(ConfigPath.SOURCES.UPDATE_DELAY_TICKS));

		// init metrics
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// nothing...
		}

		if (getConfig().getBoolean(ConfigPath.UPDATER.ENABLE)) {
			runUpdater(getServer().getConsoleSender(), getConfig().getInt(ConfigPath.UPDATER.UPDATE_DELAY_TICKS));
		}
	}

	@Override
	public void onDisable() {
		LightSourceAPI.getSearchMachine().shutdown();
		LightSourceAPI.getSourceManager().getSourceList().clear();
		LightSourceAPI.getItemManager().getItems().clear();
		LightSourceAPI.getFlagManager().getFlags().clear();

		getServer().getScheduler().cancelTasks(this);
	}

	public static Plugin getInstance() {
		return plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("ls")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 0) {
					if (BungeeChatHelperClass.hasBungeeChatAPI()) {
						BungeeChatHelperClass.sendMessageAboutPlugin(player, this);
					} else {
						player.sendMessage(ChatColor.AQUA + " ------- <LightSource " + ChatColor.WHITE + getDescription().getVersion() + "> ------- ");
						player.sendMessage(ChatColor.AQUA + " Current version: " + ChatColor.WHITE + getDescription().getVersion());
						player.sendMessage(ChatColor.AQUA + " Server name: " + ChatColor.WHITE + getServer().getName());
						player.sendMessage(ChatColor.AQUA + " Server version: " + ChatColor.WHITE + getServer().getVersion());
						player.sendMessage(ChatColor.AQUA + " Source code: " + ChatColor.WHITE + "http://github.com/BeYkeRYkt/LightSource/");
						player.sendMessage(ChatColor.AQUA + " Developer: " + ChatColor.WHITE + "BeYkeRYkt");
						player.sendMessage("");
						player.sendMessage(ChatColor.WHITE + " Licensed under: " + ChatColor.AQUA + "MIT License");
					}
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("create")) {
						LightSourceAPI.sendMessage(player, ChatColor.RED + "Need more arguments!");
						LightSourceAPI.sendMessage(player, ChatColor.RED + "/ls create [level 1-15]");
					} else if (args[0].equalsIgnoreCase("delete")) {
						if (LightAPI.deleteLight(player.getLocation(), getConfig().getBoolean(ConfigPath.GENERAL.ADD_TO_ASYNC_LIGHTING_QUEUE))) {
							for (ChunkInfo info : LightAPI.collectChunks(player.getLocation())) {
								LightAPI.updateChunk(info);
							}
							LightSourceAPI.sendMessage(player, ChatColor.GREEN + "Light on your position (x, y, z) has been deleted!");
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "Failed to remove the light. Houston, we have a problem?");
						}
					} else if (args[0].equalsIgnoreCase("update")) {
						if (player.hasPermission("ls.updater") || player.isOp()) {
							runUpdater(player, 2);
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "You don't have permission!");
						}
					} else if (args[0].equalsIgnoreCase("info")) {
						LightSourceAPI.sendMessage(player, ChatColor.RED + "Need more arguments!");
						LightSourceAPI.sendMessage(player, ChatColor.RED + "/ls info [flag name]");
					} else {
						LightSourceAPI.sendMessage(player, ChatColor.RED + "Hmm... This command does not exist. Are you sure write correctly?");
					}
				} else if (args.length >= 2) {
					if (args[0].equalsIgnoreCase("create")) {
						int level = Integer.parseInt(args[1]);
						if (level > 15) {
							level = 15;
						}
						if (LightAPI.createLight(player.getLocation(), level, getConfig().getBoolean(ConfigPath.GENERAL.ADD_TO_ASYNC_LIGHTING_QUEUE))) {
							LightSourceAPI.sendMessage(player, ChatColor.GREEN + "Light on your position (x, y, z) has been placed!");
							for (ChunkInfo info : LightAPI.collectChunks(player.getLocation())) {
								LightAPI.updateChunk(info);
							}
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "Failed to place the light. Houston, we have a problem?");
						}
					} else if (args[0].equalsIgnoreCase("delete")) {
						if (LightAPI.deleteLight(player.getLocation(), getConfig().getBoolean(ConfigPath.GENERAL.ADD_TO_ASYNC_LIGHTING_QUEUE))) {
							for (ChunkInfo info : LightAPI.collectChunks(player.getLocation())) {
								LightAPI.updateChunk(info);
							}
							LightSourceAPI.sendMessage(player, ChatColor.GREEN + "Light on your position (x, y, z) has been deleted!");
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "Failed to remove the light. Houston, we have a problem?");
						}
					} else if (args[0].equalsIgnoreCase("update")) {
						if (player.hasPermission("ls.updater") || player.isOp()) {
							runUpdater(player, 2);
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "You don't have permission!");
						}
					} else if (args[0].equalsIgnoreCase("info")) {
						if (LightSourceAPI.getFlagManager().hasFlag(args[1])) {
							LightSourceAPI.sendMessage(player, "Getting information about flag: " + ChatColor.AQUA + args[1]);
							FlagExecutor flag = LightSourceAPI.getFlagManager().getFlag(args[1]);
							int maxArgs = flag.getMaxArgs();
							if (maxArgs < 0) {
								LightSourceAPI.sendMessage(player, "Max arguments: " + ChatColor.AQUA + "Infinity");
							} else {
								LightSourceAPI.sendMessage(player, "Max arguments: " + ChatColor.AQUA + maxArgs);
							}
							LightSourceAPI.sendMessage(player, "Description: " + ChatColor.AQUA + flag.getDescription());
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "Hmm... This flag does not exist. Are you sure write correctly?");
						}
					} else {
						LightSourceAPI.sendMessage(player, ChatColor.RED + "Hmm... This command does not exist. Are you sure write correctly?");
					}
				}
			} else {
				LightSourceAPI.sendMessage(sender, ChatColor.RED + "Sorry, but right now it is not available :(");
			}
		}
		return true;
	}

	public void initDefaultConfig() {
		// Config
		try {
			File file = new File(getDataFolder(), "sources.yml");
			if (!file.exists()) {
				FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
				if (fc != null) {
					// Sea lantern
					fc.set("sealatern.material", "SEA_LANTERN");
					fc.set("sealatern.lightlevel", 15);
					List<String> listSeaLatern = new ArrayList<String>();
					// listSeaLatern.add("permission:lightsource.sealatern");
					listSeaLatern.add("update:false:true");
					listSeaLatern.add("delete_light:false");
					fc.set("sealatern.flags", listSeaLatern);

					// Lava bucket
					fc.set("lavabucket.material", "LAVA_BUCKET");
					fc.set("lavabucket.lightlevel", 15);
					List<String> listLavaBucket = new ArrayList<String>();
					// listLavaBucket.add("permission:lightsource.lavabucket");
					listLavaBucket.add("update:false:true");
					listLavaBucket.add("delete_light:false");
					fc.set("lavabucket.flags", listLavaBucket);

					// Glowstone
					fc.set("glowstone.material", "GLOWSTONE");
					fc.set("glowstone.lightlevel", 15);
					List<String> listGlowstone = new ArrayList<String>();
					// listGlowstone.add("permission:lightsource.glowstone");
					listGlowstone.add("update:false:true");
					listGlowstone.add("delete_light:false");
					fc.set("glowstone.flags", listGlowstone);

					// Redstone Lamp
					fc.set("redstonelamp.material", "REDSTONE_LAMP_ON");
					fc.set("redstonelamp.lightlevel", 15);
					List<String> listRedstoneLamp = new ArrayList<String>();
					// listRedstoneLamp.add("permission:lightsource.redstonelamp");
					listRedstoneLamp.add("update:false:true");
					listRedstoneLamp.add("delete_light:false");
					fc.set("redstonelamp.flags", listRedstoneLamp);

					// Torch
					fc.set("torch.material", "TORCH");
					fc.set("torch.lightlevel", 14);
					List<String> listTorch = new ArrayList<String>();
					// listTorch.add("permission:lightsource.torch");
					listTorch.add("update:false:true");
					listTorch.add("delete_light:false");
					listTorch.add("play_effect:smoke:0:1");
					listTorch.add("play_effect:flame:0:1");
					fc.set("torch.flags", listTorch);

					// End rod
					fc.set("endrod.material", "END_ROD");
					fc.set("endrod.lightlevel", 14);
					List<String> listEndRod = new ArrayList<String>();
					// listEndRod.add("permission:lightsource.endrod");
					listEndRod.add("update:false:true");
					listEndRod.add("delete_light:false");
					fc.set("endrod.flags", listEndRod);

					// Burning Furnace
					fc.set("furnace.material", "BURNING_FURNACE");
					fc.set("furnace.lightlevel", 13);
					List<String> listFurnace = new ArrayList<String>();
					// listFurnace.add("permission:lightsource.furnace");
					listFurnace.add("update:false:true");
					listFurnace.add("delete_light:false");
					fc.set("furnace.flags", listFurnace);

					// Redstone Ore
					fc.set("redstoneore.material", "GLOWING_REDSTONE_ORE");
					fc.set("redstoneore.lightlevel", 9);
					List<String> listRedstoneOre = new ArrayList<String>();
					// listRedstoneOre.add("permission:lightsource.redstoneore");
					listRedstoneOre.add("update:false:true");
					listRedstoneOre.add("delete_light:false");
					fc.set("redstoneore.flags", listRedstoneOre);

					// Redstone Torch
					fc.set("redstonetorch.material", "REDSTONE_TORCH_ON");
					fc.set("redstonetorch.lightlevel", 9);
					List<String> listRedstoneStone = new ArrayList<String>();
					// listRedstoneStone.add("permission:lightsource.redstonetorch");
					listRedstoneStone.add("update:false:true");
					listRedstoneStone.add("delete_light:false");
					fc.set("redstonetorch.flags", listRedstoneStone);

					// Ender chest
					fc.set("enderchest.material", "ENDER_CHEST");
					fc.set("enderchest.lightlevel", 6);
					List<String> listEnderChest = new ArrayList<String>();
					// listEnderChest.add("permission:lightsource.enderchest");
					listEnderChest.add("update:false:true");
					listEnderChest.add("delete_light:false");
					fc.set("enderchest.flags", listEnderChest);

					// Test item
					fc.set("testItem.material", "BEDROCK");
					fc.set("testItem.lightlevel", 10);
					fc.set("testItem.displayname", "DEV_TEST");
					List<String> listTest = new ArrayList<String>();
					listTest.add("entity:PLAYER:DROPPED_ITEM");
					listTest.add("permission:lightsource.testItem");
					listTest.add("world:world:world_nether:world_the_end");
					listTest.add("update:true:true");
					listTest.add("play_effect:ENDER_SIGNAL:0:1");
					listTest.add("play_effect:HEART:0:1");
					listTest.add("play_effect:MOBSPAWNER_FLAMES:0:1");
					listTest.add("delete_light:true");
					fc.set("testItem.flags", listTest);

					fc.save(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void runUpdater(final CommandSender sender, int delay) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {

			@Override
			public void run() {
				Version version = Version.parse(getDescription().getVersion());
				Updater updater;
				try {
					updater = new Updater(version, getConfig().getString(ConfigPath.UPDATER.REPO));

					Response response = updater.getResult();
					if (response == Response.SUCCESS) {
						LightSourceAPI.sendMessage(sender, ChatColor.WHITE + "New update is available: " + ChatColor.YELLOW + updater.getLatestVersion() + ChatColor.WHITE + "!");
						UpdateType update = UpdateType.compareVersion(updater.getVersion().toString());
						LightSourceAPI.sendMessage(sender, ChatColor.WHITE + "Repository: " + getConfig().getString(ConfigPath.UPDATER.REPO));
						LightSourceAPI.sendMessage(sender, ChatColor.WHITE + "Update type: " + update.getName());
						if (update == UpdateType.MAJOR) {
							LightSourceAPI.sendMessage(sender, ChatColor.RED + "WARNING ! A MAJOR UPDATE! Not updating plugins may produce errors after starting the server! Notify developers about update.");
						}
						if (getConfig().getBoolean(ConfigPath.UPDATER.VIEW_CHANGELOG)) {
							LightSourceAPI.sendMessage(sender, ChatColor.WHITE + "Changes: ");
							sender.sendMessage(updater.getChanges());// for normal view
						}
					} else if (response == Response.REPO_NOT_FOUND) {
						LightSourceAPI.sendMessage(sender, ChatColor.RED + "Repo not found! Check that your repo exists!");
					} else if (response == Response.REPO_NO_RELEASES) {
						LightSourceAPI.sendMessage(sender, ChatColor.RED + "Releases not found! Check your repo!");
					} else if (response == Response.NO_UPDATE) {
						LightSourceAPI.sendMessage(sender, ChatColor.GREEN + "You are running the latest version!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, delay);
	}

	private void generateConfig(File file) {
		FileConfiguration fc = getConfig();
		if (!file.exists()) {
			fc.options().header(getDescription().getName() + " v" + getDescription().getVersion() + " Configuration" + "\nby BeYkeRYkt");
			fc.set("version", configVer);
			fc.set(ConfigPath.GENERAL.ADD_TO_ASYNC_LIGHTING_QUEUE, false);
			fc.set(ConfigPath.UPDATER.ENABLE, true);
			fc.set(ConfigPath.UPDATER.REPO, "BeYkeRYkt/LightSource");
			fc.set(ConfigPath.UPDATER.UPDATE_DELAY_TICKS, 40);
			fc.set(ConfigPath.UPDATER.VIEW_CHANGELOG, false);
			fc.set(ConfigPath.SOURCES.UPDATE_DELAY_TICKS, 10);
			fc.set(ConfigPath.SOURCES.SEARCH.SEARCH_PLAYERS, true);
			fc.set(ConfigPath.SOURCES.SEARCH.SEARCH_ENTITIES, true);
			fc.set(ConfigPath.SOURCES.SEARCH.SEARCH_ITEMS, true);
			fc.set(ConfigPath.SOURCES.SEARCH.SEARCH_RADIUS, 20);
			fc.set(ConfigPath.SOURCES.SEARCH.SEARCH_DELAY_TICKS, 5);
			saveConfig();
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		if (getConfig().getBoolean(ConfigPath.UPDATER.VIEW_CHANGELOG)) {
			if (player.hasPermission("ls.updater")) {
				runUpdater(player, getConfig().getInt(ConfigPath.UPDATER.UPDATE_DELAY_TICKS));
			}
		}
	}
}
