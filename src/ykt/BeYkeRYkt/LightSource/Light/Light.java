package ykt.BeYkeRYkt.LightSource.Light;

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
			LightAPI.createLightSource(getLocation(), lightlevel, true);
			needupdate = false;
		}
		}else{
			if(!needupdate){
			needupdate = true;
			}
	        LightAPI.deleteLightSource(getLocation());
			setLocation(location);
			LightAPI.createLightSource(getLocation(), lightlevel, false);
		}	
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
}