package ykt.BeYkeRYkt.LightSource.OtherListeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ykt.BeYkeRYkt.LightSource.LightAPI;

import com.bergerkiller.bukkit.common.events.EntityMoveEvent;
import com.bergerkiller.bukkit.common.events.EntityRemoveEvent;
import com.bergerkiller.bukkit.common.events.EntityRemoveFromServerEvent;

public class EntityListener implements Listener {

	@EventHandler
	public void onEntityMove(EntityMoveEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) entity;

			if (le.getFireTicks() > 0) {
				if (event.getFromX() != event.getToX()
						|| event.getFromY() != event.getToY()
						|| event.getFromZ() != event.getToZ()) {

					Location from = new Location(le.getWorld(),
							event.getFromX(), event.getFromY(),
							event.getFromZ());
					Location to = new Location(le.getWorld(), event.getToX(),
							event.getToY(), event.getToZ());

					LightAPI.deleteLightSource(from);
					LightAPI.createLightSource(to, 12);

				}
			} else {
				Location from = new Location(le.getWorld(), event.getFromX(),
						event.getFromY(), event.getFromZ());
				LightAPI.deleteLightSourceAndUpdate(from);
			}
		}
	}

	@EventHandler
	public void onItemRemove(EntityRemoveEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) entity;
			Location loc = le.getLocation();

			if (le.getFireTicks() > 0) {
				LightAPI.deleteLightSourceAndUpdate(loc);
			}

		}
	}

	@EventHandler
	public void onItemRemoveFromServer(EntityRemoveFromServerEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) entity;
			Location loc = le.getLocation();

			if (le.getFireTicks() > 0) {
				LightAPI.deleteLightSourceAndUpdate(loc);
			}

		}
	}
}