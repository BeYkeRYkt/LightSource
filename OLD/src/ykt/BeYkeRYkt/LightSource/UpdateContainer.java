package ykt.BeYkeRYkt.LightSource;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import ykt.BeYkeRYkt.LightSource.gravitydevelopment.updater.Updater;
import ykt.BeYkeRYkt.LightSource.gravitydevelopment.updater.Updater.ReleaseType;

public class UpdateContainer implements Listener{
	
	
	/**
	 * 
	 * UPDATE SYSTEM
	 * 
	 */

	
	public static boolean update = false;
	public static String name = "";
	public static ReleaseType type = null;
	public static String version = "";
	public static String link = "";
	public static int id = 77176;
	public static File file = null;
	private static String delimiter = "^v|[\\s_-]v";
	
	public UpdateContainer(final File file){
		Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(LightSource.getInstance(), new BukkitRunnable() {
	        @Override
	        public void run() {
			Updater updater = new Updater(LightSource.getInstance(), id, file, Updater.UpdateType.NO_DOWNLOAD, false);

			name = updater.getLatestName();
			version = updater.getLatestGameVersion();
			type = updater.getLatestType();
			link = updater.getLatestFileLink();
			
			if(checkUpdate(updater.getLatestName())){
			update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
			
		    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE +"An update is available: " + name + ", a " + type + " for " + version + " available at " + link);
			}
	        }
	        }, 0, 432000);

		UpdateContainer.file = file;
		
		Bukkit.getPluginManager().registerEvents(this, LightSource.getInstance());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
	  Player player = event.getPlayer();
	  if(player.hasPermission("lightsource.admin") && UpdateContainer.update || player.isOp() && UpdateContainer.update)
	  {
	    player.sendMessage(ChatColor.BLUE + "[LightSourceUpdater] " +"An update is available: " + UpdateContainer.name + ", a " + UpdateContainer.type + " for " + UpdateContainer.version + " available at " + UpdateContainer.link);
	  }
	}
	
	
	public boolean checkUpdate(String title){
		
		//Beta
		String pluginVersion = LightSource.getInstance().getDescription().getVersion();
		String remoteVersion = title.split(delimiter)[1].split(" ")[0];
		if(title.split(delimiter).length == 2){
			String[] parts = remoteVersion.split("\\.");
			//New version
			String new1 = parts[0];
			String new2 = parts[1];
			String new3 = parts[2];
			int newMajor = Integer.valueOf(new1);
			int newMinor = Integer.valueOf(new2);
			int newVersion = Integer.valueOf(new3);
			
			
			//Old version
			String[] old = pluginVersion.split("\\.");
			String old1 = old[0];
			String old2 = old[1];
			String old3 = old[2];
			int oldMajor = Integer.valueOf(old1);
			int oldMinor = Integer.valueOf(old2);
			int oldVersion = Integer.valueOf(old3);
			
			if(newMajor > oldMajor || newMinor > oldMinor || newVersion > oldVersion){
				return true;
			}
		}
		return false;
	}
}