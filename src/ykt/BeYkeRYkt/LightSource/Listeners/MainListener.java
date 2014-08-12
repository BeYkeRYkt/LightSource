package ykt.BeYkeRYkt.LightSource.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.Light.ItemManager;
import ykt.BeYkeRYkt.LightSource.Light.Light;

public class MainListener implements Listener{
		
	//@EventHandler
	//public void onEntitySpawn(CreatureSpawnEvent event){
		//if(ItemManager.isLightSource(event.getEntity().getEquipment().getItemInHand())){
		    //LightAPI.deleteLightSource(event.getLocation());
		    //LightAPI.updateChunk(event.getLocation(), event.getLocation().getChunk());
		//}else
		//if(ItemManager.isLightSource(event.getEntity().getEquipment().getHelmet())){
		    //LightAPI.deleteLightSource(event.getLocation());
		    //LightAPI.updateChunk(event.getLocation(), event.getLocation().getChunk());
		//}
	//}
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event){
		for(Light light: LightAPI.getSources()){
			if(event.getChunk().getX() == light.getLocation().getChunk().getX()
			&& event.getChunk().getZ() == light.getLocation().getChunk().getZ()){
		    LightAPI.deleteLightSource(light.getLocation());
		    light.updateChunks();
			}
		}
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event){
		for(Light light: LightAPI.getSources()){
			if(event.getChunk().getX() == light.getLocation().getChunk().getX()
			&& event.getChunk().getZ() == light.getLocation().getChunk().getZ()){
		    LightAPI.deleteLightSource(light.getLocation());
		    light.updateChunks();
			}
		}
	}
	
	@EventHandler
	public void onItemHeldChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItem(event.getNewSlot());
		Location loc = player.getLocation();

		if (LightSource.getInstance().getDB().getWorld(event.getPlayer().getWorld().getName()) && LightSource.getInstance().getDB().isPlayerLight()){
			if (item != null && ItemManager.isLightSource(item)) {
			Light light = new Light(player, loc);
			LightAPI.createLightSource(loc, ItemManager.getLightLevel(item));
			light.updateChunks();
			LightAPI.addSource(light);
			}else if(item == null || item != null && ItemManager.isLightSource(item)){
			if(LightAPI.checkEntityID(event.getPlayer()) != null){
			LightAPI.deleteLightSource(loc);
			}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(LightAPI.checkEntityID(event.getPlayer()) != null){
		Light light = LightAPI.checkEntityID(event.getPlayer());
	    LightAPI.deleteLightSource(light.getLocation());
	    light.updateChunks();
	    LightAPI.removeSource(light);
		}
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		if(LightAPI.checkEntityID(event.getPlayer()) != null){
		Light light = LightAPI.checkEntityID(event.getPlayer());
	    LightAPI.deleteLightSource(light.getLocation());
	    light.updateChunks();
	    LightAPI.removeSource(light);
		}
	}
	
	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		if(LightAPI.checkEntityID(event.getPlayer()) != null){
		Light light = LightAPI.checkEntityID(event.getPlayer());
	    LightAPI.deleteLightSource(light.getLocation());
	    light.updateChunks();
		}
	}
	
	@EventHandler
	public void onPlayerDropLight(PlayerDropItemEvent event) {
		for(Light light: LightAPI.getSources()){
		if(light.getOwner().getType() == EntityType.PLAYER){
			Player player = (Player) light.getOwner();
		if(player.getName().equals(event.getPlayer())){
	    LightAPI.deleteLightSource(light.getLocation());
	    light.updateChunks();
		}
		}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(LightAPI.checkEntityID(event.getEntity()) != null){
		Light light = LightAPI.checkEntityID(event.getEntity());
	    LightAPI.deleteLightSource(light.getLocation());
	    light.updateChunks();
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if(LightAPI.checkEntityID(event.getPlayer()) != null){
		Light light = LightAPI.checkEntityID(event.getPlayer());
	    LightAPI.deleteLightSource(light.getLocation());
	    light.updateChunks();
		}
	}
	
	@EventHandler
	public void onPlayerChangeWorlds(PlayerChangedWorldEvent event) {
		if(LightAPI.checkEntityID(event.getPlayer()) != null){
		Light light = LightAPI.checkEntityID(event.getPlayer());
	    LightAPI.deleteLightSource(light.getLocation());
	    light.updateChunks();
		}
	}
	
}