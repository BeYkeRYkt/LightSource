package ykt.BeYkeRYkt.LightSource.nms;

import org.bukkit.Location;

import ykt.BeYkeRYkt.LightSource.sources.ChunkCoords;

public interface NMSHandler {

	public void createLight(Location location, int light);

	public void deleteLight(Location location);

	public void updateChunk(ChunkCoords chunk);

	public void initWorlds();

	public void unloadWorlds();
}