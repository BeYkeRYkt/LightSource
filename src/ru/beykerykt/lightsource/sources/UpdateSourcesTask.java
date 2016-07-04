package ru.beykerykt.lightsource.sources;

import org.bukkit.Bukkit;

import ru.beykerykt.lightsource.LightSource;
import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.flags.FlagHelper;

public class UpdateSourcesTask implements Runnable {

	private boolean isStarted;
	private int id;

	public void start(int ticks) {
		if (!isStarted) {
			isStarted = true;
			id = Bukkit.getScheduler().runTaskTimerAsynchronously(LightSource.getInstance(), this, 0, ticks).getTaskId();
		}
	}

	public void shutdown() {
		if (isStarted) {
			isStarted = false;
			Bukkit.getScheduler().cancelTask(id);
		}
	}

	@Override
	public void run() {
		for (Source source : LightSourceAPI.getSourceManager().getSourceList()) {
			if (source.shouldExecute()) {
				source.onUpdate();
			} else {
				FlagHelper.callEndingFlag(source);
				LightSourceAPI.getSourceManager().removeSource(source);
			}
		}
	}
}
