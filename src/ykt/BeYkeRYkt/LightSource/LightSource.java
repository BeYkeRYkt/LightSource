package ykt.BeYkeRYkt.LightSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import ykt.BeYkeRYkt.LightSource.GUIMenu.Menus;
import ykt.BeYkeRYkt.LightSource.Light.ItemManager;
import ykt.BeYkeRYkt.LightSource.Light.Light;
import ykt.BeYkeRYkt.LightSource.Listeners.GUIListener;
import ykt.BeYkeRYkt.LightSource.Listeners.MainListener;


public class LightSource extends JavaPlugin{

	private static LightSource plugin;
	private LightAPI api;
	private int taskId = 0;
	private ItemManager manager;
    private StaggeredRunnable run;
    private LightConfig config;
	
	@Override
	public void onEnable() {
		plugin = this;
		api = new LightAPI();
		if(api.getNMSHandler() != null){
		PluginDescriptionFile pdfFile = getDescription();
		manager = new ItemManager();

		try {
			FileConfiguration fc = getConfig();
			if (!new File(getDataFolder(), "config.yml").exists()) {
				fc.options().header(
						"LightSource v" + pdfFile.getVersion()
								+ " Configuration" + "\nHave fun :3"
								+ "\nby BeYkeRYkt");
				fc.addDefault("PlayerLight", true);
				fc.addDefault("EntityLight", false);
				fc.addDefault("ItemLight", false);
				fc.addDefault("Enable-updater", true);
				fc.addDefault("Debug", false);
				fc.addDefault("RadiusSendPackets", 64);
				fc.addDefault("Delay-before-starting-staggered-runnable-ticks", 0);
				fc.addDefault("Delay-between-restarting-staggered-runnable-ticks", 5);

				List<World> worlds = getServer().getWorlds();
				for (World world : worlds) {
					fc.addDefault("Worlds." + world.getName(), true);
				}
				fc.options().copyDefaults(true);
				saveConfig();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		loadItems();
		
		//mcstats
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    // Failed to submit the stats :-(
		}
		
		config = new LightConfig();
		
		//Update
		if(config.isUpdater()){
			this.getLogger().info("Enabling update system...");
			new UpdateContainer(this.getFile());
		}
				
		Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
		Bukkit.getPluginManager().registerEvents(new MainListener(), this);
		
		this.getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is now enabled. Have fun.");
		
		//Start Runnable --> DoGzzz old tests light system for my server ;P
		taskId = Bukkit.getScheduler().runTaskTimer(this, new LightTask(), 0L, 5L).getTaskId();
		
		//TESTED
		run = new StaggeredRunnable(LightSource.getInstance(), LightAPI.getChunksForUpdate());
		run.start();
		
		}
	}

	
	public void loadItems(){
		createExampleItems();
		getItemManager().loadItems();
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		ItemManager.getList().clear();
		HandlerList.unregisterAll(this);
		int index;
		for(index = LightAPI.getSources().size() - 1; index >= 0; --index){
		    Light light = LightAPI.getSources().get(index);
			LightAPI.deleteLightSource(light.getLocation());
		    light.updateChunks();
			LightAPI.getSources().remove(light);
		}
		config.save();
		api = null;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("ls")){
				Menus.openMainMenu(player);
			}else if(cmd.getName().equalsIgnoreCase("light")){
				Menus.openLightCreatorMenu(player);
			}
		}
		return true;
	}
	
	public int getTaskId(){
		return taskId;
	}
	
	public LightConfig getDB(){
		return config;
	}
	
	public void createExampleItems() {
		try {
			FileConfiguration fc = getItemManager().getConfig();
			
			if (!new File(getDataFolder(), "Items.yml").exists()) {			
				
				fc.addDefault("Lava.material", "LAVA");
				fc.addDefault("Lava.lightlevel", 15);
				
				fc.addDefault("StationLava.material", "STATIONARY_LAVA");
				fc.addDefault("StationLava.lightlevel", 15);
				
				fc.addDefault("Fire.material", "FIRE");
				fc.addDefault("Fire.lightlevel", 115);
				
				fc.addDefault("Jack.material", "JACK_O_LATERN");
				fc.addDefault("Jack.lightlevel", 15);
				
				fc.addDefault("LavaBucket.material", "LAVA_BUCKET");
				fc.addDefault("LavaBucket.lightlevel", 15);
				
				fc.addDefault("Torch.material", "TORCH");
				fc.addDefault("Torch.lightlevel", 14);
				
				fc.addDefault("Glowstone.material", "GLOWSTONE");
				fc.addDefault("Glowstone.lightlevel", 14);
				
				fc.addDefault("BlazeRod.material", "BLAZE_ROD");
				fc.addDefault("BlazeRod.lightlevel", 5);
				
				fc.addDefault("Redstone.material", "REDSTONE_TORCH_ON");
				fc.addDefault("Redstone.lightlevel", 9);
				
				fc.options().copyDefaults(true);
				getItemManager().saveConfig();
				fc.options().copyDefaults(false);
				getItemManager().reloadConfig();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
		
	public static LightSource getInstance() {
		return plugin;
	}

	public LightAPI getAPI() {
		return api;
	}

	/**
	 * @return the manager
	 */
	public ItemManager getItemManager() {
		return manager;
	}

}