package ykt.BeYkeRYkt.LightSource.nmsUtils;

import org.bukkit.Location;
import org.bukkit.World;

public interface NMSInterface {

	public enum UpdateLocationType{
		PLAYER_LOCATION, MOB_LOCATION, ITEM_LOCATION; 
	}
	public void recalculateBlockLighting(World world, int x, int y, int z);

	
	/**
	 * Create light with level at a location.
	 * 
	 * @param loc
	 *            - which block to update.
	 * @param level
	 *            - the new light level.
	 */
	public void createLightSource(Location loc, int level, UpdateLocationType type);

	/**
	 * Destroy light with level at a location (Update)
	 * 
	 * @param loc
	 *            - location to the block that was updated.
	 */
	public void deleteLightSourceAndUpdate(Location loc, UpdateLocationType type);

	
	
	//FOR ALL
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
	public void updateChunk(World world, Location loc, UpdateLocationType type);
	
	
	/**
	 * Delete light with level at a location. (Not update)
	 * 
	 * @param loc
	 *            - which block to update.
	 */
	public void deleteLightSource(Location loc);

}