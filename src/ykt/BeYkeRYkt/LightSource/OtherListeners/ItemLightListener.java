package ykt.BeYkeRYkt.LightSource.OtherListeners;

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

public class ItemLightListener implements Listener {

	@EventHandler
	public void onItemMove(EntityMoveEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof Item) {
			Item item_entity = (Item) entity;
			ItemStack item = item_entity.getItemStack();

			if(item_entity.getLocation().getChunk().isLoaded()){
			
			if (item != null && ItemManager.isTorchLight(item)) {
				if (event.getFromX() != event.getToX()
						|| event.getFromY() != event.getToY()
						|| event.getFromZ() != event.getToZ()) {

					Location from = new Location(item_entity.getWorld(),
							event.getFromX(), event.getFromY(),
							event.getFromZ());
					Location to = new Location(item_entity.getWorld(),
							event.getToX(), event.getToY(), event.getToZ());

					LightAPI.deleteLightSourceForEntity(from);
					LightAPI.createLightSourceForEntity(to,
							ItemManager.getLightLevel(item));
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
			Location loc = item_entity.getLocation();

			if(item_entity.getLocation().getChunk().isLoaded()){
			if (item != null && ItemManager.isTorchLight(item)) {
				LightAPI.deleteLightSourceAndUpdateForEntity(loc);
			}
			}
		}
	}

	@EventHandler
	public void onItemRemoveFromServer(EntityRemoveFromServerEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Item) {
			Item item_entity = (Item) entity;
			ItemStack item = item_entity.getItemStack();
			Location loc = item_entity.getLocation();

			if(item_entity.getLocation().getChunk().isLoaded()){
			if (item != null && ItemManager.isTorchLight(item)) {
				LightAPI.deleteLightSourceAndUpdateForEntity(loc);
			}
			}
		}
	}

	@EventHandler
	public void onPlayerPickupLight(PlayerPickupItemEvent event) {
		Location loc = event.getPlayer().getLocation();
		Item entity = event.getItem();
		ItemStack item = entity.getItemStack();

		if (item != null && ItemManager.isTorchLight(item)) {
			LightAPI.deleteLightSourceAndUpdateForEntity(entity.getLocation());
			LightSource.getInstance().getTorchPlayers().add(event.getPlayer().getName());
		}
	}

}