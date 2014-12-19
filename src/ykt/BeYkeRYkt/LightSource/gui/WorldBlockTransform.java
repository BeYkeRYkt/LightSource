package ykt.BeYkeRYkt.LightSource.gui;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;

//For World_Name
public class WorldBlockTransform {

    private Material block;
    private World world;

    public WorldBlockTransform(World world) {
        this.world = world;

        // check material
        if (world.getEnvironment() == Environment.NORMAL) {
            block = Material.GRASS;
        } else if (world.getEnvironment() == Environment.NETHER) {
            block = Material.NETHERRACK;
        } else if (world.getEnvironment() == Environment.THE_END) {
            block = Material.ENDER_STONE;
        } else {
            block = Material.DIRT;
        }
    }

    /**
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    /**
     * @return the block
     */
    public Material getBlock() {
        return block;
    }
}