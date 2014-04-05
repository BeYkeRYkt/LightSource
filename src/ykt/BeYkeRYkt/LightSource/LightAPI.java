package ykt.BeYkeRYkt.LightSource;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import ykt.BeYkeRYkt.LightSource.nmsUtils.NMSHandler_v1_5_R3;
import ykt.BeYkeRYkt.LightSource.nmsUtils.NMSHandler_v1_6_R3;
import ykt.BeYkeRYkt.LightSource.nmsUtils.NMSHandler_v1_7_R1;
import ykt.BeYkeRYkt.LightSource.nmsUtils.NMSHandler_v1_7_R2;
import ykt.BeYkeRYkt.LightSource.nmsUtils.NMSInterface;


public class LightAPI{	

    private String version = null;
    private static NMSInterface nms;
    
    public LightAPI(){
    	this.version = Bukkit.getBukkitVersion();
	    this.version = this.version.substring(0, 6);
	    this.version = this.version.replaceAll("-", "");
	    initNMS();
    }
    
    private void initNMS(){
    	if(this.version.startsWith("1.5.2")){
    		this.nms = new NMSHandler_v1_5_R3();
    		LightSource.getInstance().getLogger().info("Founded CraftBukkit/Spigot 1.5.2! Using NMSHandler for 1.5.2.");
    	}else if(this.version.startsWith("1.6.4")){
    		this.nms = new NMSHandler_v1_6_R3();
    		LightSource.getInstance().getLogger().info("Founded CraftBukkit/Spigot 1.6.4! Using NMSHandler for 1.6.4");
    	}else if(this.version.startsWith("1.7.2")){
    		this.nms = new NMSHandler_v1_7_R1();
    		LightSource.getInstance().getLogger().info("Founded CraftBukkit/Spigot 1.7.2! Using NMSHandler for 1.7.2.");
    	}else if(this.version.startsWith("1.7.5")){
    		this.nms = new NMSHandler_v1_7_R2();
    		LightSource.getInstance().getLogger().info("Founded CraftBukkit/Spigot 1.7.5! Using NMSHandler for 1.7.5.");
    	}else{
    		LightSource.getInstance().getLogger().info("Unsupported version of the server. Off.");
    		Bukkit.getPluginManager().disablePlugin(LightSource.getInstance());
    	}
    }
	
	public NMSInterface getNMSHandler(){
		return nms;
	}
    
    /**
     * Create light with level at a location. 
     * @param loc - which block to update.
     * @param level - the new light level.
     */
    public static void createLightSource(Location loc, int level) {

    	nms.createLightSource(loc, level);
    	
        if(LightSource.getInstance().getConfig().getBoolean("Debug")){
        LightSource.getInstance().getLogger().info("Created light at location: X=" + loc.getBlockX() + " Y=" + loc.getBlockY() + " Z=" + loc.getBlockZ());
        }
    }

    
    /**
     * Delete light with level at a location.
     * @param loc - which block to update.
     */
    public static void deleteLightSourceStatic(Location loc){
    	nms.deleteLightSourceStatic(loc);
    	
        if(LightSource.getInstance().getConfig().getBoolean("Debug")){
            LightSource.getInstance().getLogger().info("Deleted light at location: X=" + loc.getBlockX() + " Y=" + loc.getBlockY() + " Z=" + loc.getBlockZ());
         }
    }

    /**
     * Create light with level at a location. 
     * @param loc - which block to update.
     * @param level - the new light level.
     */
    public static void createLightSourceStatic(Location loc, int level) {

    	nms.createLightSourceStatic(loc, level);
    	
        if(LightSource.getInstance().getConfig().getBoolean("Debug")){
        LightSource.getInstance().getLogger().info("Created light at location: X=" + loc.getBlockX() + " Y=" + loc.getBlockY() + " Z=" + loc.getBlockZ());
        }
    }

    
    /**
     * Delete light with level at a location. (Not update)
     * @param loc - which block to update.
     */
    public static void deleteLightSource(Location loc){
    	nms.deleteLightSource(loc);
    }
    
    
	/**
	* Destroy light with level at a location (Update)
	* @param loc - location to the block that was updated.
	*/
    public static void deleteLightSourceAndUpdate(Location loc){

    	nms.deleteLightSourceAndUpdate(loc);
        if(LightSource.getInstance().getConfig().getBoolean("Debug")){
           LightSource.getInstance().getLogger().info("Deleted light at location: X=" + loc.getBlockX() + " Y=" + loc.getBlockY() + " Z=" + loc.getBlockZ());
        }
    }  
    
	/**
	* Gets all the chunks touching/diagonal to the chunk the location is in and updates players with them.
	* @param loc - location to the block that was updated.
	* @param nmsWorld - world
	*/
    public static void updateChunk(World world, Location loc) {

    	nms.updateChunk(world, loc);
    }		 
}