package ykt.BeYkeRYkt.LightSource.nmsUtils;

import org.bukkit.Location;
import org.bukkit.World;

public interface NMSInterface {

	public void recalculateBlockLighting(World world, int x, int y, int z);

	
	/**
	 * Create light with level at a location.
	 * 
	 * @param loc
	 *            - which block to update.
	 * @param level
	 *            - the new light level.
	 */
	public void createLightSource(Location loc, int level);

	/**
	 * Destroy light with level at a location (Update)
	 * 
	 * @param loc
	 *            - location to the block that was updated.
	 */
	public void deleteLightSourceAndUpdate(Location loc);
	
	
	/**
	 * Create light with level at a location.
	 * 
	 * @param loc
	 *            - which block to update.
	 * @param level
	 *            - the new light level.
	 *            
	 * @param blocklevel
	 *            - the block lightlevel
	 */
	public void createLightSourceStatic(Location loc, int level);
	
	
	/**
	 * Destroy light with level at a location (Update)
	 * 
	 * @param loc
	 *            - location to the block that was updated.
	 */
	public void deleteLightSourceStatic(Location loc);

	
	/**
	 * Gets all the chunks touching/diagonal to the chunk the location is in and
	 * updates players with them.
	 * 
	 * @param loc
	 *            - location to the block that was updated.
	 * @param nmsWorld
	 *            - world
	 * @param type
	 *            - Update light type
	 */
	public void updateChunk(World world, Location loc);
	
	
	/**
	 * Delete light with level at a location. (Not update)
	 * 
	 * @param loc
	 *            - which block to update.
	 */
	public void deleteLightSource(Location loc);


}