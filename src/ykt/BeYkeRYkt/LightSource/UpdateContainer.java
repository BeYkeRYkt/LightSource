package ykt.BeYkeRYkt.LightSource;

import java.io.File;

import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.ReleaseType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
	private static final String delimiter = "^v|[\\s_-]v";
	
	public UpdateContainer(File file){
		Updater updater = new Updater(LightSource.getInstance(), id, file, Updater.UpdateType.NO_DOWNLOAD, false); // Start Updater but just do a version check

		if(checkUpdate(updater.getLatestName())){
		update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE; // Determine if there is an update ready for us
		}
		
		name = updater.getLatestName(); // Get the latest name
		version = updater.getLatestGameVersion(); // Get the latest game version
		type = updater.getLatestType(); // Get the latest file's type
		link = updater.getLatestFileLink(); // Get the latest link
		this.file = file;
		
		Bukkit.getPluginManager().registerEvents(this, LightSource.getInstance());
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
	  Player player = event.getPlayer();
	  if(player.hasPermission("lightsource.admin") && this.update)
	  {
	    player.sendMessage(ChatColor.BLUE + "[LightSourceUpdater] " +"An update is available: " + this.name + ", a " + this.type + " for " + this.version + " available at " + this.link);
	    // Will look like - An update is available: AntiCheat v1.5.9, a release for CB 1.6.2-R0.1 available at http://media.curseforge.com/XYZ
	    player.sendMessage("Type /ls update if you would like to automatically update.");
	  }
	}
	
	
	public boolean checkUpdate(String title){
		String pluginVersion = LightSource.getInstance().getDescription().getVersion();
		
		if (title.split(delimiter).length == 2) {
			String newVersion = title.split(delimiter)[1].split(" ")[0];
			
			double oldDouble = Double.valueOf(pluginVersion);
			double newDouble = Double.valueOf(newVersion);
			
			if(oldDouble > newDouble){
				return false;
			}
		}
		
		return true;
	}
}