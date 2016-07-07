package ru.beykerykt.lightsource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.beykerykt.lightapi.utils.Metrics;
import ru.beykerykt.lightsource.items.flags.basic.DeleteLightExecutor;
import ru.beykerykt.lightsource.items.flags.basic.EntityCheckExecutor;
import ru.beykerykt.lightsource.items.flags.basic.PermissionCheckExecutor;
import ru.beykerykt.lightsource.items.flags.basic.PlayEffectExecutor;
import ru.beykerykt.lightsource.items.flags.basic.UpdateExecutor;
import ru.beykerykt.lightsource.items.flags.basic.WorldCheckExecutor;
import ru.beykerykt.lightsource.items.loader.YamlLoader;
import ru.beykerykt.lightsource.sources.UpdateSourcesTask;
import ru.beykerykt.lightsource.sources.search.SearchSourcesMachine;

public class LightSource extends JavaPlugin {

	private static LightSource plugin;
	private SearchSourcesMachine searchMachine;
	private UpdateSourcesTask updateTask;

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
		this.searchMachine = new SearchSourcesMachine();
		searchMachine.start(10);
		this.updateTask = new UpdateSourcesTask();
		updateTask.start(10);

		initDefaultConfig();
		new YamlLoader().loadFromFile(new File(getDataFolder(), "sources.yml"));

		// init metrics
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// nothing...
		}
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

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
	}

	public static Plugin getInstance() {
		return plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("ls")) {
			LightSourceAPI.sendMessage(sender, ChatColor.RED + "Sorry, but right now it is not available :(");
		}
		return true;
	}
}
