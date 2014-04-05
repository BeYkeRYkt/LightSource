package ykt.BeYkeRYkt.LightSource.OtherListeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.TorchLight.ItemManager;

import com.bergerkiller.bukkit.common.events.EntityMoveEvent;
import com.bergerkiller.bukkit.common.events.EntityRemoveEvent;
import com.bergerkiller.bukkit.common.events.EntityRemoveFromServerEvent;

public class RadiusItemLightListener implements Listener {

	private static HashMap<Integer, Location> loc = new HashMap<Integer, Location>();
	
	public static HashMap<Integer, Location> getLocations(){
		return loc;
	}
	
	@EventHandler
	public void onItemMove(EntityMoveEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof Item) {
			Item item_entity = (Item) entity;
			ItemStack item = item_entity.getItemStack();

			
			if (item != null && ItemManager.isTorchLight(item)) {
				if (event.getFromX() != event.getToX()|| event.getFromY() != event.getToY()|| event.getFromZ() != event.getToZ()) {
					Location from = new Location(item_entity.getWorld(),event.getFromX(), event.getFromY(),event.getFromZ());
					Location to = new Location(item_entity.getWorld(),event.getToX(), event.getToY(), event.getToZ());
					
				      if (!loc.containsKey(item_entity.getEntityId())) {
				            loc.put(item_entity.getEntityId(), from);
				      }
				    double d = loc.get(item_entity.getEntityId()).distance(to);
				    if (d >= LightSource.getInstance().getConfig().getDouble("Radius-update")) {
					LightAPI.deleteLightSource(loc.get(item_entity.getEntityId()));
					LightAPI.createLightSource(to,ItemManager.getLightLevel(item));
					loc.put(item_entity.getEntityId(), to);
				   }
				}
			}
		}
	}

	@EventHandler
	public void onItemRemove(EntityRemoveEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Item) {
			Item item_entity = (Item) entity;
			ItemStack item = item_entity.getItemStack();


			if (item != null && ItemManager.isTorchLight(item)) {
				Location loc = this.loc.get(item_entity.getEntityId());
				LightAPI.deleteLightSourceAndUpdate(loc);
			}
			
		}
	}

	@EventHandler
	public void onItemRemoveFromServer(EntityRemoveFromServerEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Item) {
			Item item_entity = (Item) entity;
			ItemStack item = item_entity.getItemStack();

			if (item != null && ItemManager.isTorchLight(item)) {
				Location loc = this.loc.get(item_entity.getEntityId());
				LightAPI.deleteLightSourceAndUpdate(loc);
			}
			
		}
	}

	@EventHandler
	public void onPlayerPickupLight(PlayerPickupItemEvent event) {
		Location loc = event.getPlayer().getLocation();
		Item entity = event.getItem();
		ItemStack item = entity.getItemStack();

		if (item != null && ItemManager.isTorchLight(item)) {
			LightAPI.deleteLightSourceAndUpdate(this.loc.get(entity.getEntityId()));
		}
	}

}