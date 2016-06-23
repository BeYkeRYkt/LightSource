package ru.beykerykt.lightsource.items.flags.basic;

import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.chunks.ChunkInfo;
import ru.beykerykt.lightsource.items.flags.TickableFlagExecutor;
import ru.beykerykt.lightsource.sources.Source;

public class UpdateExecutor implements TickableFlagExecutor {

	@Override
	public void onTick(Source source, String[] args) {
		if (args.length > 0) {
			boolean flag = Boolean.getBoolean(args[0]);
			LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), flag);
			LightAPI.createLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), source.getItem().getLevelLight(), flag);
			for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
				LightAPI.updateChunks(info);
			}
		} else {
			LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), true);
			LightAPI.createLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), source.getItem().getLevelLight(), true);
			for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
				LightAPI.updateChunks(info);
			}
		}
	}
}