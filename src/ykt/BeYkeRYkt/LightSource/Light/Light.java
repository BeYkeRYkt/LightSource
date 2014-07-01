package ykt.BeYkeRYkt.LightSource.Light;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import ykt.BeYkeRYkt.LightSource.LightAPI;

public class Light{
	
	private Entity owner;
	private Location location;

	public Light(Entity entity, Location loc){
		this.owner = entity;
		this.setLocation(loc);
	}

	/**
	 * @return the owner
	 */
	public Entity getOwner() {
		return owner;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
		}
	
	public ArrayList<Chunk> getChunks(){
		ArrayList<Chunk> cs = new ArrayList<Chunk>();
		 
		for(int x=-1; x<=1; x++) {
		for(int z=-1; z<=1; z++) {
		Chunk chunk = getLocation().clone().add(16 * x, 0, 16 * z).getChunk();
		if(!cs.contains(chunk)){	
		cs.add(chunk);
		}
		}
		}
		return cs;	
	}
	
	public void updateChunks(){
		for(Chunk chunk: getChunks()){
			LightAPI.updateChunk(getLocation(), chunk);
		}
	}
}