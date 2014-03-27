package ykt.BeYkeRYkt.LightSource.HeadLamp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;

public class HeadLampListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getHelmet();

		if (LightSource.getInstance().getConfig().getBoolean("Worlds." + event.getPlayer().getWorld().getName()) || event.getPlayer().isOp()){
		// Checking player
		if (LightSource.getInstance().getHeadPlayers().contains(player)) {
			if (item != null && HeadManager.isHeadLamp(item)) {
				if (event.getFrom().getBlock().getLocation().getBlockX() != event.getTo().getBlock().getLocation().getBlockX()
						|| event.getFrom().getBlock().getLocation().getBlockY() != event.getTo().getBlock().getLocation()
								.getBlockY()
						|| event.getFrom().getBlock().getLocation().getBlockZ() != event.getTo().getBlock().getLocation()
								.getBlockZ()) {

					LightAPI.deleteLightSource(event.getFrom().getBlock().getLocation());
					LightAPI.createLightSource(event.getTo().getBlock().getLocation(),
							HeadManager.getLightLevel(item));
				}
			} else if (item != null && !HeadManager.isHeadLamp(item)
					|| item == null) {
				LightAPI.deleteLightSourceAndUpdate(event.getFrom().getBlock().getLocation());
				LightSource.getInstance().getHeadPlayers().remove(player);

				if (LightSource.getInstance().getConfig().getBoolean("Debug")) {
					LightSource.getInstance().getLogger()
							.info("Player removed from the group");
				}

			}

		} else if (!LightSource.getInstance().getHeadPlayers().contains(player)) {
			if (item != null && HeadManager.isHeadLamp(item)) {
				LightAPI.createLightSource(player.getLocation().getBlock().getLocation(),
						HeadManager.getLightLevel(item));
				LightSource.getInstance().getHeadPlayers().add(player);

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
		Location loc = event.getPlayer().getLocation().getBlock().getLocation();

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
		}

	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		Location loc = event.getPlayer().getLocation().getBlock().getLocation();

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
		}

	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Location loc = event.getEntity().getLocation().getBlock().getLocation();

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getEntity())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
		}

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Location loc = event.getPlayer().getLocation().getBlock().getLocation();

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
			LightSource.getInstance().getHeadPlayers()
					.remove(event.getPlayer());
		}

	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Location loc = event.getPlayer().getLocation().getBlock().getLocation();

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
			LightSource.getInstance().getHeadPlayers()
					.remove(event.getPlayer());
		}

	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Location loc = event.getPlayer().getLocation().getBlock().getLocation();

		if (LightSource.getInstance().getHeadPlayers()
				.contains(event.getPlayer())) {
			LightAPI.deleteLightSourceAndUpdate(loc);
			LightSource.getInstance().getHeadPlayers()
					.remove(event.getPlayer());
		}

	}

}