package ykt.BeYkeRYkt.LightSource.OtherListeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.nmsUtils.NMSInterface.UpdateLocationType;

import com.bergerkiller.bukkit.common.events.EntityMoveEvent;
import com.bergerkiller.bukkit.common.events.EntityRemoveEvent;
import com.bergerkiller.bukkit.common.events.EntityRemoveFromServerEvent;

public class EntityListener implements Listener {

	
	/**
	 * REASON: MORREEEEE LAAGGGZZZZZ
	 * 
	 * 
	 */
			
	
	@EventHandler
	public void onEntityMove(EntityMoveEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType().isAlive()) {

		for(Player players: entity.getWorld().getPlayers()){
			
			if(players.getLocation().distance(entity.getLocation()) <= Bukkit.getViewDistance() * 16){
			if (entity.getFireTicks() > 0) {
				if (event.getFromX() != event.getToX()
						|| event.getFromY() != event.getToY()
						|| event.getFromZ() != event.getToZ()) {

					Location from = new Location(entity.getWorld(),
							event.getFromX(), event.getFromY(),
							event.getFromZ());
					Location to = new Location(entity.getWorld(), event.getToX(),
							event.getToY(), event.getToZ());

					LightAPI.deleteLightSource(from);
					LightAPI.createLightSource(to, 12,UpdateLocationType.MOB_LOCATION);

				}
			} else {
				Location from = new Location(entity.getWorld(), event.getFromX(),
						event.getFromY(), event.getFromZ());
				LightAPI.deleteLightSourceAndUpdate(from, UpdateLocationType.MOB_LOCATION);
			}
		
			//View <
		}else{
			Location from = new Location(entity.getWorld(), event.getFromX(),
					event.getFromY(), event.getFromZ());
			LightAPI.deleteLightSourceAndUpdate(from, UpdateLocationType.MOB_LOCATION);
		}
			
			
		}
		}
	}

	@EventHandler
	public void onItemRemove(EntityRemoveEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType().isAlive()) {
			Location loc = entity.getLocation();

			if (entity.getFireTicks() > 0) {
				LightAPI.deleteLightSourceAndUpdate(loc, UpdateLocationType.MOB_LOCATION);
			}
			
		}
	}

	@EventHandler
	public void onItemRemoveFromServer(EntityRemoveFromServerEvent event) {
		Entity entity = event.getEntity();
		if (entity.getType().isAlive()) {
           Location loc = entity.getLocation();

			if (entity.getFireTicks() > 0) {
				LightAPI.deleteLightSourceAndUpdate(loc, UpdateLocationType.MOB_LOCATION);
			}
			
		}
	}
}