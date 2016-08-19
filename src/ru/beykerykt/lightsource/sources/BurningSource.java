package ru.beykerykt.lightsource.sources;

import org.bukkit.entity.Entity;

import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.chunks.ChunkInfo;
import ru.beykerykt.lightsource.LightSourceAPI;

public class BurningSource extends OwnedSource {

	public BurningSource(Entity entity, int lightlevel) {
		super(entity, lightlevel);
	}

	@Override
	public boolean shouldExecute() {
		boolean shoud = super.shouldExecute() && getOwner().getFireTicks() > 0;
		if (!shoud) {
			LightAPI.deleteLight(getOldLocation().getWorld(), getOldLocation().getBlockX(), getOldLocation().getBlockY(), getOldLocation().getBlockZ(), LightSourceAPI.isAsyncLightingFlag());
			LightAPI.deleteLight(getLocation().getWorld(), getLocation().getBlockX(), getLocation().getBlockY(), getLocation().getBlockZ(), LightSourceAPI.isAsyncLightingFlag());
			for (ChunkInfo info : LightAPI.collectChunks(getLocation().getWorld(), getLocation().getBlockX(), getLocation().getBlockY(), getLocation().getBlockZ())) {
				LightAPI.updateChunk(info);
			}
		}
		return shoud;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (getOldLocation().getBlockX() != getLocation().getBlockX() || getOldLocation().getBlockY() != getLocation().getBlockY() || getOldLocation().getBlockZ() != getLocation().getBlockZ()) {
			LightAPI.deleteLight(getOldLocation().getWorld(), getOldLocation().getBlockX(), getOldLocation().getBlockY(), getOldLocation().getBlockZ(), LightSourceAPI.isAsyncLightingFlag());
			LightAPI.createLight(getLocation().getWorld(), getLocation().getBlockX(), getLocation().getBlockY(), getLocation().getBlockZ(), getLightLevel(), LightSourceAPI.isAsyncLightingFlag());
			for (ChunkInfo info : LightAPI.collectChunks(getLocation().getWorld(), getLocation().getBlockX(), getLocation().getBlockY(), getLocation().getBlockZ())) {
				LightAPI.updateChunk(info);
			}
		}
	}
}
