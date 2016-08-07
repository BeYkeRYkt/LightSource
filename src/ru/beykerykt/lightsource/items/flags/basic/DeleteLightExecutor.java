package ru.beykerykt.lightsource.items.flags.basic;

import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.chunks.ChunkInfo;
import ru.beykerykt.lightsource.items.flags.EndingFlagExecutor;
import ru.beykerykt.lightsource.sources.ItemableSource;
import ru.beykerykt.lightsource.sources.Source;

public class DeleteLightExecutor implements EndingFlagExecutor {

	@Override
	public void onEnd(ItemableSource source, String[] args) {
		if (args.length > 0) {
			boolean flag = Boolean.parseBoolean(args[0]);
			LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), flag);
			LightAPI.deleteLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), flag);
			for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
				LightAPI.updateChunk(info);
			}
		} else {
			LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), false);
			LightAPI.deleteLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), false);
			for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
				LightAPI.updateChunk(info);
			}
		}
	}

}
