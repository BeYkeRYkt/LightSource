package ru.beykerykt.lightsource;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.beykerykt.lightsource.items.Item;
import ru.beykerykt.lightsource.items.flags.basic.DeleteLightExecutor;
import ru.beykerykt.lightsource.items.flags.basic.EntityCheckExecutor;
import ru.beykerykt.lightsource.items.flags.basic.PermissionCheckExecutor;
import ru.beykerykt.lightsource.items.flags.basic.UpdateExecutor;
import ru.beykerykt.lightsource.runnables.SearchSourcesTask;
import ru.beykerykt.lightsource.runnables.UpdateSourcesTask;

public class LightSource extends JavaPlugin {

	private static LightSource plugin;

	@Override
	public void onEnable() {
		this.plugin = this;
		getServer().getScheduler().runTaskTimer(getInstance(), new UpdateSourcesTask(10), 0, 10).getTaskId();
		getServer().getScheduler().runTaskTimer(getInstance(), new SearchSourcesTask(10), 0, 10).getTaskId();

		// register checkers
		LightSourceAPI.getFlagManager().registerFlag("permission", new PermissionCheckExecutor());
		LightSourceAPI.getFlagManager().registerFlag("entity", new EntityCheckExecutor());

		// register tickers
		LightSourceAPI.getFlagManager().registerFlag("update", new UpdateExecutor());

		// register post execturos
		LightSourceAPI.getFlagManager().registerFlag("delete_light", new DeleteLightExecutor());
		
		Item item = new Item("torch", Material.TORCH, 0, 14);
		List<String> list = new ArrayList<String>();
		list.add("update:true");
		list.add("delete_light");
		item.setFlagsList(list);
		LightSourceAPI.getItemManager().addItem(item);
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
	}

	public static Plugin getInstance() {
		return plugin;
	}

}
