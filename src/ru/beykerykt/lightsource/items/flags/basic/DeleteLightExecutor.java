package ru.beykerykt.lightsource.items.flags.basic;

import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.chunks.ChunkInfo;
import ru.beykerykt.lightsource.items.flags.EndingFlagExecutor;
import ru.beykerykt.lightsource.sources.Source;

public class DeleteLightExecutor implements EndingFlagExecutor {

	@Override
	public void onEnd(Source source, String[] args) {
		if (args.length == 0) {
			LightAPI.deleteLight(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ(), true);
			for (ChunkInfo info : LightAPI.collectChunks(source.getOldLocation().getWorld(), source.getOldLocation().getBlockX(), source.getOldLocation().getBlockY(), source.getOldLocation().getBlockZ())) {
				LightAPI.updateChunks(info);
			}

			LightAPI.deleteLight(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ(), true);
			for (ChunkInfo info : LightAPI.collectChunks(source.getLocation().getWorld(), source.getLocation().getBlockX(), source.getLocation().getBlockY(), source.getLocation().getBlockZ())) {
				LightAPI.updateChunks(info);
			}
		} else {
			for (int i = 0; i < args.length; i++) {
				// TODO: delay removing light
			}
		}
	}

}
