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
import org.bukkit.Color;
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
import ru.beykerykt.lightapi.updater.Response;
import ru.beykerykt.lightapi.updater.UpdateType;
import ru.beykerykt.lightapi.updater.Updater;
import ru.beykerykt.lightapi.updater.Version;
import ru.beykerykt.lightapi.utils.Metrics;
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
import ru.beykerykt.lightsource.utils.BungeeChatHelperClass;

public class LightSource extends JavaPlugin {

	private static LightSource plugin;
	private UpdateSourcesTask updateTask;
	private Configuration config;
	private int configVer = 1;

	@Override
	public void onLoad() {
		this.config = new Configuration();

		// register checkers
		LightSourceAPI.getFlagManager().registerFlag("permission", new PermissionCheckExecutor());
		LightSourceAPI.getFlagManager().registerFlag("entity", new EntityCheckExecutor());
		LightSourceAPI.getFlagManager().registerFlag("world", new WorldCheckExecutor());

		// register tickers
		LightSourceAPI.getFlagManager().registerFlag("update", new UpdateExecutor());
		if (!Bukkit.getVersion().equalsIgnoreCase("CraftBukkit")) {
			LightSourceAPI.sendMessage(getServer().getConsoleSender(), Color.RED + "Sorry, but flag 'play_effect' using SpigotAPI code. This flag doesn't work in CraftBukkit.");
			LightSourceAPI.getFlagManager().registerFlag("play_effect", new PlayEffectExecutor());
		}

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
		config.exportFromConfiguration(getConfig());

		if (getConfiguration().isSearchPlayers()) {
			LightSourceAPI.getSearchMachine().addTask(new PlayerSearchTask());
		}
		if (getConfiguration().isSearchEntities()) {
			LightSourceAPI.getSearchMachine().addTask(new EntitySearchTask(getConfiguration().getSearchRadius()));
		}
		if (getConfiguration().isSearchItems()) {
			LightSourceAPI.getSearchMachine().addTask(new ItemEntitySearchTask(getConfiguration().getSearchRadius()));
		}
		LightSourceAPI.getSearchMachine().start(getConfiguration().getSearchDelayTicks());

		this.updateTask = new UpdateSourcesTask();
		updateTask.start(getConfiguration().getUpdateDelayTicks());

		// init metrics
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// nothing...
		}

		if (getConfiguration().isUpdaterEnable()) {
			runUpdater(getServer().getConsoleSender(), getConfiguration().getUpdaterDelayTicks());
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

	public Configuration getConfiguration() {
		return config;
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
						if (LightAPI.deleteLight(player.getLocation(), getConfiguration().isAddToLightingQueue())) {
							for (ChunkInfo info : LightAPI.collectChunks(player.getLocation())) {
								LightAPI.updateChunk(info);
							}
							LightSourceAPI.sendMessage(player, ChatColor.GREEN + "Light on your position (x, y, z) has been deleted!");
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "Failed to remove the light. Houston, we have a problem?");
						}
					}
				} else if (args.length >= 2) {
					if (args[0].equalsIgnoreCase("create")) {
						int level = Integer.parseInt(args[1]);
						if (level > 15) {
							level = 15;
						}
						if (LightAPI.createLight(player.getLocation(), level, getConfiguration().isAddToLightingQueue())) {
							LightSourceAPI.sendMessage(player, ChatColor.GREEN + "Light on your position (x, y, z) has been placed!");
							for (ChunkInfo info : LightAPI.collectChunks(player.getLocation())) {
								LightAPI.updateChunk(info);
							}
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "Failed to place the light. Houston, we have a problem?");
						}
					} else if (args[0].equalsIgnoreCase("delete")) {
						if (LightAPI.deleteLight(player.getLocation(), getConfiguration().isAddToLightingQueue())) {
							for (ChunkInfo info : LightAPI.collectChunks(player.getLocation())) {
								LightAPI.updateChunk(info);
							}
							LightSourceAPI.sendMessage(player, ChatColor.GREEN + "Light on your position (x, y, z) has been deleted!");
						} else {
							LightSourceAPI.sendMessage(player, ChatColor.RED + "Failed to remove the light. Houston, we have a problem?");
						}
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
					fc.set("torch.material", "TORCH");
					fc.set("torch.lightlevel", 14);
					List<String> listTorch = new ArrayList<String>();
					listTorch.add("permission:lightsource.torch");
					listTorch.add("update:true:false");
					listTorch.add("delete_light:true");
					listTorch.add("play_effect:smoke:0:1");
					listTorch.add("play_effect:flame:0:1");
					fc.set("torch.flags", listTorch);

					fc.set("glowstone.material", "GLOWSTONE");
					fc.set("glowstone.lightlevel", 15);
					List<String> listGlowstone = new ArrayList<String>();
					listGlowstone.add("permission:lightsource.glowstone");
					listGlowstone.add("update:true:true");
					listGlowstone.add("delete_light:true");
					fc.set("glowstone.flags", listGlowstone);

					fc.set("testItem.material", "DIAMOND");
					fc.set("testItem.lightlevel", 10);
					fc.set("testItem.displayname", "HAHAHA, NICE JOKE :)");
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
					updater = new Updater(version, getConfiguration().getRepo());

					Response response = updater.getResult();
					if (response == Response.SUCCESS) {
						LightSourceAPI.sendMessage(sender, ChatColor.WHITE + "New update is available: " + ChatColor.YELLOW + updater.getLatestVersion() + ChatColor.WHITE + "!");
						UpdateType update = UpdateType.compareVersion(updater.getVersion().toString());
						LightSourceAPI.sendMessage(sender, ChatColor.WHITE + "Repository: " + getConfiguration().getRepo());
						LightSourceAPI.sendMessage(sender, ChatColor.WHITE + "Update type: " + update.getName());
						if (update == UpdateType.MAJOR) {
							LightSourceAPI.sendMessage(sender, ChatColor.RED + "WARNING ! A MAJOR UPDATE! Not updating plugins may produce errors after starting the server! Notify developers about update.");
						}
						if (getConfiguration().isViewChangelog()) {
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
			fc.set("add-to-async-lighting-queue", true);
			fc.set("updater.enable", true);
			fc.set("updater.repo", "BeYkeRYkt/LightSource");
			fc.set("updater.update-delay-ticks", 40);
			fc.set("updater.view-changelog", false);
			fc.set("sources.search.search-players", true);
			fc.set("sources.search.search-entities", true);
			fc.set("sources.search.search-items", true);
			fc.set("sources.search.search-radius", 20);
			fc.set("sources.search.search-delay-ticks", 5);
			fc.set("sources.update-delay-ticks", 10);
			saveConfig();
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		if (getConfiguration().isUpdaterEnable()) {
			if (player.hasPermission("lightapi.updater")) {
				runUpdater(player, getConfiguration().getUpdaterDelayTicks());
			}
		}
	}
}
