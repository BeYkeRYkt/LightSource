package ru.beykerykt.lightsource.items.flags.basic;

import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.chunks.ChunkInfo;
import ru.beykerykt.lightsource.items.flags.TickableFlagExecutor;
import ru.beykerykt.lightsource.sources.ItemableSource;

public class UpdateExecutor implements TickableFlagExecutor {

	@Override
	public void onTick(ItemableSource source, String[] args) {
		if (args.length == 1) {
			boolean flag = Boolean.parseBoolean(args[0]);
			LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), flag);
			LightAPI.createLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), source.getLightLevel(), flag);
			for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
				LightAPI.updateChunk(info);
			}
			return;
		} else if (args.length >= 2) {
			boolean flag = Boolean.parseBoolean(args[0]);
			boolean saveMove = Boolean.parseBoolean(args[1]);
			if (saveMove) {
				if (source.getOldLocation().getBlockX() != source.getLocation().getBlockX() || source.getOldLocation().getBlockY() != source.getLocation().getBlockY() || source.getOldLocation().getBlockZ() != source.getLocation().getBlockZ()) {
					LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), flag);
					LightAPI.createLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), source.getLightLevel(), flag);
					for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
						LightAPI.updateChunk(info);
					}
				}
				return;
			}
			LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), flag);
			LightAPI.createLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), source.getLightLevel(), flag);
			for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
				LightAPI.updateChunk(info);
			}
			return;
		} else {
			LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), false);
			LightAPI.createLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), source.getLightLevel(), false);
			for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
				LightAPI.updateChunk(info);
			}
		}
	}
}
