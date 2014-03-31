package ykt.BeYkeRYkt.LightSource.OtherListeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.HeadLamp.HeadManager;

public class RadiusHeadListener implements Listener {

	private static HashMap<String, Location> pLocations = new HashMap<String, Location>();
	
	
	public static HashMap<String, Location >getLocations(){
		return pLocations;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getHelmet();

		if (LightSource.getInstance().getConfig().getBoolean("Worlds." + event.getPlayer().getWorld().getName()) || event.getPlayer().isOp()){
		// Checking player
		if (LightSource.getInstance().getHeadPlayers().contains(player.getName())) {
			if (item != null && HeadManager.isHeadLamp(item)) {

			      if (!pLocations.containsKey(player.getName())) {
			            pLocations.put(player.getName(), event.getFrom());
			        }
				
			      double d = pLocations.get(player.getName()).distance(event.getTo());
			      if (d >= LightSource.getInstance().getConfig().getDouble("Radius-update")) {

					LightAPI.deleteLightSource(pLocations.get(player.getName()));
					LightAPI.createLightSource(event.getTo(),HeadManager.getLightLevel(item));
					pLocations.put(player.getName(), event.getTo());
				}
			} else if (item != null && !HeadManager.isHeadLamp(item)
					|| item == null || item.getType() == Material.AIR) {
				LightAPI.deleteLightSourceAndUpdate(pLocations.get(player.getName()));
				LightSource.getInstance().getHeadPlayers().remove(player.getName());
				pLocations.remove(player.getName());
				
				
				if (LightSource.getInstance().getConfig().getBoolean("Debug")) {
					LightSource.getInstance().getLogger()
							.info("Player removed from the group");
				}

			}

		} else if (!LightSource.getInstance().getHeadPlayers().contains(player.getName())) {
			if (item != null && HeadManager.isHeadLamp(item)) {
			      if (!pLocations.containsKey(player.getName())) {
			            pLocations.put(player.getName(), event.getFrom());
			        }

				LightAPI.createLightSource(player.getLocation(),
						HeadManager.getLightLevel(item));
				LightSource.getInstance().getHeadPlayers().add(player.getName());

				if (LightSource.getInstance().getConfig().getBoolean("Debug")) {
					LightSource.getInstance().getLogger()
							.info("Player added to the group");
				}
			
			}
		}
		}
	}

	@EventHandler
	public void onPlayerChangeWorlds(PlayerChangedWorldEvent event) {
		if(!pLocations.containsKey(event.getPlayer())) return;
		Location loc = pLocations.get(event.getPlayer().getName());

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer().getName())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
		}

	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if(!pLocations.containsKey(event.getPlayer())) return;
		Location loc = pLocations.get(event.getPlayer().getName());
		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer().getName())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
		}

	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(!pLocations.containsKey(event.getEntity())) return;
		Location loc = pLocations.get(event.getEntity().getName());

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getEntity().getName())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
		}

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(!pLocations.containsKey(event.getPlayer())) return;
		Location loc = pLocations.get(event.getPlayer().getName());

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer().getName())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
			LightSource.getInstance().getHeadPlayers()
					.remove(event.getPlayer().getName());
			pLocations.remove(event.getPlayer().getName());
		}

	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		if(!pLocations.containsKey(event.getPlayer())) return;
		Location loc = pLocations.get(event.getPlayer().getName());

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer().getName())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
			LightSource.getInstance().getHeadPlayers()
					.remove(event.getPlayer().getName());
			pLocations.remove(event.getPlayer().getName());
		}

	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		if(!pLocations.containsKey(event.getPlayer())) return;
		Location loc = pLocations.get(event.getPlayer().getName());
		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer().getName())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
			LightSource.getInstance().getHeadPlayers()
					.remove(event.getPlayer().getName());
		}
	}

	@EventHandler
	public void onPlayerDropLight(PlayerDropItemEvent event) {
		if(!pLocations.containsKey(event.getPlayer())) return;
		Location loc = pLocations.get(event.getPlayer().getName());
		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer().getName())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
			LightSource.getInstance().getHeadPlayers()
					.remove(event.getPlayer().getName());
		}
	}
}