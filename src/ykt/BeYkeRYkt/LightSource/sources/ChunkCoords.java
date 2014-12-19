package ykt.BeYkeRYkt.LightSource.sources;

import org.bukkit.Chunk;
import org.bukkit.World;

public class ChunkCoords {

    private final int x;
    private final int z;
    private final World world;

    public ChunkCoords(Chunk chunk) {
        this.x = chunk.getX();
        this.z = chunk.getZ();
        this.world = chunk.getWorld();
    }

    /**
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return the z
     */
    public int getZ() {
        return z;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "ChunkCoords{" + "x=" + getX() + "z=" + getZ() + '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof ChunkCoords)) {
            return false;
        } else {
            ChunkCoords chunk = (ChunkCoords) other;
            return getWorld().getName().equals(chunk.getWorld().getName()) && this.getX() == chunk.getX() && this.getZ() == chunk.getZ();
        }
    }
}