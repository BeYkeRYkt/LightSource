package ru.beykerykt.lightsource.sources.search;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.sources.BurningSource;
import ru.beykerykt.lightsource.sources.Source;

public class BurningEntitySearchTask implements SearchTask {

	private double radius = 20;

	public BurningEntitySearchTask(double radius) {
		this.radius = radius;
	}

	@Override
	public void onTick() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
				if (LightSourceAPI.getSourceManager().isSource(entity))
					continue;
				if (entity.getFireTicks() > 0) {
					Source source = new BurningSource(entity, 12);
					LightSourceAPI.getSourceManager().addSource(source);
				}
			}
		}
	}
}
