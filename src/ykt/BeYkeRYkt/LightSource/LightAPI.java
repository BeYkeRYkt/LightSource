package ykt.BeYkeRYkt.LightSource;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import ykt.BeYkeRYkt.LightSource.gui.GUIManager;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.nms.NMSHandler;
import ykt.BeYkeRYkt.LightSource.nms.NMSHandler_v_1_8;
import ykt.BeYkeRYkt.LightSource.sources.ChunkCoords;
import ykt.BeYkeRYkt.LightSource.sources.SourceManager;

public class LightAPI {

	private static ItemManager manager;
	private GUIManager gui;
	private static SourceManager source;
	public static CommandSender BUKKIT_SENDER = Bukkit.getConsoleSender();
	private static NMSHandler nmsHandler;
	private int taskBurnId;

	public LightAPI() {
		this.nmsHandler = new NMSHandler_v_1_8();

		manager = new ItemManager();
		gui = new GUIManager(LightSource.getInstance());
		source = new SourceManager(LightSource.getInstance());

		// Start burn task
		// taskBurnId =
		// Bukkit.getScheduler().runTaskTimer(LightSource.getInstance(), new
		// BurnTask(source), 0, 20).getTaskId();
	}

	public void init() {
		manager.loadItems();
		gui.load();
		source.init();
		nmsHandler.initWorlds();
	}

	public static void createLight(Location location, int lightlevel) {
		nmsHandler.createLight(location, lightlevel);
	}

	public static void deleteLight(Location location) {
		nmsHandler.deleteLight(location);
	}

	public static void updateChunk(ChunkCoords chunk) {
		nmsHandler.updateChunk(chunk);
	}

	public static ItemManager getItemManager() {
		return manager;
	}

	public GUIManager getGUIManager() {
		return gui;
	}

	public static SourceManager getSourceManager() {
		return source;
	}

	public void log(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.AQUA + "<LightSource>: " + ChatColor.WHITE + message);
	}

}