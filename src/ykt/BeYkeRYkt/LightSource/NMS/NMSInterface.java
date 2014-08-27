package ykt.BeYkeRYkt.LightSource.NMS;

import org.bukkit.Location;
import org.bukkit.World;

public interface NMSInterface {
	
	
	/**
	 * Recalculate lighting for blocks
	 * 
	 * @param world - Bukkit World
	 * @param x - X location
	 * @param y - Y location
	 * @param z - Z location             
	 */
	public void recalculateBlockLighting(World world, int x, int y, int z);

	/**
	 * Create light with level at a location.
	 * 
	 * @param loc - which block to update.
	 * @param level - the new light level.
	 * @param flag - Static light?
	 */
	public void createLightSource(Location loc, int level, boolean flag);
	
	
	/**
	 * Delete light with level at a location.
	 * 
	 * @param loc - which block to update.
	 */
	public void deleteLightSource(Location loc);
	
	
	/**
	 * 
	 * Initial Custom IWorldAccess
	 * 
	 */
	public void initWorlds();
	
	/**
	 * 
	 * Unload Custom IWorldAccess from nmsWorld.class
	 * 
	 */
	public void unloadWorlds();
	
	/**
	 * 
	 * Custom IWorldAccess for initWorlds();
	 * from: https://gist.github.com/aadnk/5841942
	 * Thanks aadnk!
	 * 
	 * @param world - Bukkit World
	 */
	public Object getLightIWorldAccess(World world);
}