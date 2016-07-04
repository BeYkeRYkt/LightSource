package ru.beykerykt.lightsource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
					List<String> list = new ArrayList<String>();
					list.add("update:true");
					list.add("delete_light:true");
					list.add("play_effect:smoke:0");
					list.add("permission:lightsource.torch");
					fc.set("torch.flags", list);
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

}
