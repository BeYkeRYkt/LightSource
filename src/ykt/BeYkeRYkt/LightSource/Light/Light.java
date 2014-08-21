package ykt.BeYkeRYkt.LightSource.Light;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import ykt.BeYkeRYkt.LightSource.LightAPI;

public class Light{
	
	private Entity owner;
	private Location location;
	private boolean needupdate = true;

	public Light(Entity entity, Location loc){
		this.owner = entity;
		this.location = loc;
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

	public void updateLight(Location location, int lightlevel){
		if(getLocation().getBlockX() == location.getBlockX() && getLocation().getBlockY() == location.getBlockY() && getLocation().getBlockZ() == location.getBlockZ()){
		if(needupdate){
		setNeedUpdate(false);
		}
		
		}else{
		setNeedUpdate(true);
		LightAPI.deleteLightSource(getLocation());
		setLocation(location);
		LightAPI.createLightSource(getLocation(), lightlevel);
		}
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

	/**
	 * @return the needupdate
	 */
	public boolean isNeedUpdate() {
		return needupdate;
	}

	/**
	 * @param needupdate the needupdate to set
	 */
	public void setNeedUpdate(boolean needupdate) {
		this.needupdate = needupdate;
	}
}