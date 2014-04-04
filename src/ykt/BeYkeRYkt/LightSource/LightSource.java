package ykt.BeYkeRYkt.LightSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateResult;
import net.gravitydevelopment.updater.Updater.UpdateType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import ykt.BeYkeRYkt.LightSource.GUIMenu.GUIListener;
import ykt.BeYkeRYkt.LightSource.HeadLamp.HeadLampListener;
import ykt.BeYkeRYkt.LightSource.HeadLamp.HeadManager;
import ykt.BeYkeRYkt.LightSource.OtherListeners.ItemLightListener;
import ykt.BeYkeRYkt.LightSource.OtherListeners.RadiusHeadListener;
import ykt.BeYkeRYkt.LightSource.OtherListeners.RadiusTorchListener;
import ykt.BeYkeRYkt.LightSource.TorchLight.ItemManager;
import ykt.BeYkeRYkt.LightSource.TorchLight.TorchLightListener;

public class LightSource extends JavaPlugin{

	private static LightSource plugin;
	private ArrayList<String> torch = new ArrayList<String>();
	private ArrayList<String> head = new ArrayList<String>();
	private ItemManager im;
	private HeadManager hm;
	private LightAPI api;
	private boolean gui;
    private ItemLightListener ill;
    
    
    @Override
    public void onLoad(){
		this.plugin = this;
		this.api = new LightAPI();
		this.hm = new HeadManager();
		this.im = new ItemManager();
		this.ill = new ItemLightListener();
		
		createExampleTorch();
		createExampleHead();
    }
    
	@Override
	public void onEnable() {
		if(api.getNMSHandler() != null){
		PluginDescriptionFile pdfFile = getDescription();

		try {
			FileConfiguration fc = getConfig();
			if (!new File(getDataFolder(), "config.yml").exists()) {
				fc.options().header(
						"LightSource v" + pdfFile.getVersion()
								+ " Configuration" + "\nHave fun :3"
								+ "\nby BeYkeRYkt");
				fc.addDefault("Enable-updater", false);
				fc.addDefault("Debug", false);
				fc.addDefault("Enable-GUI", true);
				fc.addDefault("Radius-mode", true);
				fc.addDefault("Radius-update", 4.0);
				fc.addDefault("Advanced-Listener.TorchLight", false);

				List<World> worlds = getServer().getWorlds();
				for (World world : worlds) {
					fc.addDefault("Worlds." + world.getName(), true);
				}
				fc.options().copyDefaults(true);
				saveConfig();
				fc.options().copyDefaults(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		getItemManager().loadItems();
		getHeadManager().loadItems();

		if(this.getConfig().getBoolean("Radius-mode")){
			Bukkit.getPluginManager().registerEvents(new RadiusTorchListener(), this);
			Bukkit.getPluginManager().registerEvents(new RadiusHeadListener(), this);
		}else{
		Bukkit.getPluginManager().registerEvents(new TorchLightListener(), this);
		Bukkit.getPluginManager().registerEvents(new HeadLampListener(), this);
		}
		
		
		if (this.getConfig().getBoolean("Advanced-Listener.TorchLight")) {

			registerAdvancedItemListener(true);

		}

		this.gui = getConfig().getBoolean("Enable-GUI");	
		Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
		
		Bukkit.getPluginManager().addPermission(new Permission("lightsource.admin", PermissionDefault.FALSE));
		Bukkit.getPluginManager().addPermission(new Permission("lightsource.hidden", PermissionDefault.FALSE));
		
		getCommand("ls").setExecutor(new MainCommand());
		getCommand("light").setExecutor(new LightCommand());
		
		this.getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled. Have fun.");
		
		
		if(this.getConfig().getBoolean("Enable-updater")){
		Updater updater = new Updater(this, 77176, this.getFile(), UpdateType.NO_DOWNLOAD, true);
		if(updater.getResult() == UpdateResult.UPDATE_AVAILABLE){
			Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "[LightSource]" + "New version available! " + updater.getLatestName() + " for " + updater.getLatestGameVersion());
		}
		}
		
		}
	}

	public void createExampleTorch() {
		try {
			FileConfiguration fc = getItemManager().getConfig()
					.getSourceConfig();
			if (!new File(getDataFolder(), "TorchLight.yml").exists()) {				
				fc.addDefault("DEMO.name", null);
				fc.addDefault("DEMO.material", "TORCH");
				fc.addDefault("DEMO.lightlevel", 14);

				fc.addDefault("Redstone.name", "REDSTONE_TEST");
				fc.addDefault("Redstone.material", "REDSTONE_TORCH_ON");
				fc.addDefault("Redstone.lightlevel", 8);
				
				fc.addDefault("COLOR_TEST.name", "&4TEST");
				fc.addDefault("COLOR_TEST.material", "LAVA_BUCKET");
				fc.addDefault("COLOR_TEST.lightlevel", 10);
				
				fc.options().copyDefaults(true);
				getItemManager().getConfig().saveSourceConfig();
				fc.options().copyDefaults(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createExampleHead() {
		try {
			FileConfiguration fc = getHeadManager().getConfig()
					.getSourceConfig();
			if (!new File(getDataFolder(), "HeadLamp.yml").exists()) {

				fc.addDefault("DEMO.name", null);
				fc.addDefault("DEMO.material", "GLOWSTONE");
				fc.addDefault("DEMO.lightlevel", 10);

				fc.options().copyDefaults(true);
				getHeadManager().getConfig().saveSourceConfig();
				fc.options().copyDefaults(false);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		
		Bukkit.getPluginManager().removePermission("lightsource.admin");
		Bukkit.getPluginManager().removePermission("lightsource.hidden");
		
		if(!this.getConfig().getBoolean("Radius-mode")){
			
		for(Player players : Bukkit.getOnlinePlayers()){
			LightAPI.deleteLightSourceAndUpdate(players.getLocation().getBlock().getLocation());
		}
		
		this.getLogger().info("Deleted all lights!");
		
		}else if(this.getConfig().getBoolean("Radius-mode")){
			for(Player players : Bukkit.getOnlinePlayers()){
				
				if(!RadiusHeadListener.getLocations().isEmpty()){
				Location loc = RadiusHeadListener.getLocations().get(players.getName());
				LightAPI.deleteLightSourceAndUpdate(loc);
				RadiusHeadListener.getLocations().clear();
				}
				
				if(!RadiusTorchListener.getLocations().isEmpty()){
				Location loc = RadiusTorchListener.getLocations().get(players.getName());
				LightAPI.deleteLightSourceAndUpdate(loc);
				RadiusTorchListener.getLocations().clear();
				}
				
			}
			this.getLogger().info("Deleted all lights!");
	    }
						
		
		hm.getList().clear();
		im.getList().clear();
			
		this.hm = null;
		this.im = null;
		
		this.torch.clear();
		this.head.clear();
		this.api = null;
		this.gui = false;
		
		HandlerList.unregisterAll(this);
	}
		
	public static LightSource getInstance() {
		return plugin;
	}

	public ArrayList<String> getTorchPlayers() {
		return torch;
	}

	public ArrayList<String> getHeadPlayers() {
		return head;
	}

	public HeadManager getHeadManager() {
		return hm;
	}

	public ItemManager getItemManager() {
		return im;
	}

	public LightAPI getAPI() {
		return api;
	}

	public boolean getGUI() {
		return gui;
	}
	
	public void registerAdvancedItemListener(boolean flag){
		if(flag){
		if (Bukkit.getPluginManager().getPlugin("BKCommonLib").isEnabled()) {
			this.getLogger().info("Found BKCommonLib! Enabling Advanced item listener!");
			Bukkit.getPluginManager().registerEvents(ill, this);
		} else {
			this.getLogger().info("To work needed BKCommonLib. Advanced listener does not include.");
		}
		}else if(!flag){
			this.getLogger().info("Disabling Advanced listener for items");
			
			for(Player players: Bukkit.getOnlinePlayers()){
				int view = Bukkit.getViewDistance() * 16;
				for(Entity entity: players.getNearbyEntities(view, view, view)){
					if(entity instanceof Item){
					LightAPI.deleteLightSourceAndUpdate(entity.getLocation().getBlock().getLocation());
					}
				}
			}
			HandlerList.unregisterAll(ill);
		}
	}

	public void setGUI(boolean parseBoolean) {
		this.gui = parseBoolean;
	}
}