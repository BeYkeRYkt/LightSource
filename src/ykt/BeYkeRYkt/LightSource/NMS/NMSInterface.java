package ykt.BeYkeRYkt.LightSource.NMS;


import org.bukkit.Chunk;
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
	 * @param LightType - LightType for light update
	 * @param loc - which block to update.
	 * @param level - the new light level.
	 */
	public void createLightSource(Location loc, int level);
	
	/**
	 * Gets all the chunks touching/diagonal to the chunk the location is in and
	 * updates players with them.
	 * 
	 * @param Chunk - Bukkit chunk
	 * @param loc - Location for update 
	 */
	public void updateChunk(Location loc, Chunk chunk);
	
	
	/**
	 * Delete light with level at a location. (Not update)
	 * 
	 * @param LightType - LightType for light update
	 * 
	 * @param loc - which block to update.
	 */
	public void deleteLightSource(Location loc);




}