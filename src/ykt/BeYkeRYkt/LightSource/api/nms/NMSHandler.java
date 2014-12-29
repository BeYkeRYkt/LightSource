package ykt.BeYkeRYkt.LightSource.api.nms;

import org.bukkit.Location;

import ykt.BeYkeRYkt.LightSource.api.sources.ChunkCoords;

public interface NMSHandler {

    public void createLight(Location location, int light);

    public void deleteLight(Location location);

    public void updateChunk(ChunkCoords chunk);

    public void initWorlds();

    public void unloadWorlds();
}