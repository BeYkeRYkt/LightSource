package ykt.BeYkeRYkt.LightSource;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import ykt.BeYkeRYkt.LightSource.editor.EditorManager;
import ykt.BeYkeRYkt.LightSource.gui.GUIManager;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.nms.NMSHandler;
import ykt.BeYkeRYkt.LightSource.nms.NMSHandler_v_1_7_10;
import ykt.BeYkeRYkt.LightSource.nms.NMSHandler_v_1_8;
import ykt.BeYkeRYkt.LightSource.sources.ChunkCoords;
import ykt.BeYkeRYkt.LightSource.sources.SourceManager;

public class LightAPI {

    private static ItemManager manager;
    private GUIManager gui;
    private static SourceManager source;
    public static CommandSender BUKKIT_SENDER = Bukkit.getConsoleSender();
    private static NMSHandler nmsHandler;
    private static EditorManager editor;

    public LightAPI() {
        String version = Bukkit.getBukkitVersion();
        version = version.substring(0, 6);
        version = version.replaceAll("-", "");

        if (version.startsWith("1.7.10")) {
            nmsHandler = new NMSHandler_v_1_7_10();
            log(BUKKIT_SENDER, ChatColor.GREEN + "Enabling NMS Handler for Minecraft 1.7.10");
        } else if (version.startsWith("1.8")) {
            nmsHandler = new NMSHandler_v_1_8();
            log(BUKKIT_SENDER, ChatColor.GREEN + "Enabling NMS Handler for Minecraft 1.8");
        } else {
            log(BUKKIT_SENDER, ChatColor.RED + "Sorry. Your MC server not supported this plugin.");
            Bukkit.getPluginManager().disablePlugin(LightSource.getInstance());
        }
        if (getNMSHandler() != null) {
            manager = new ItemManager();
            source = new SourceManager(LightSource.getInstance());
            gui = new GUIManager();
            editor = new EditorManager();
        }
    }

    public void init() {
        manager.loadItems();
        source.init();
        editor.init();
        gui.load();
        nmsHandler.initWorlds();
    }

    public static void createLight(Location location, int lightlevel) {
        nmsHandler.createLight(location, lightlevel);
    }

    public static void deleteLight(Location location, boolean needUpdateChunk) {
        nmsHandler.deleteLight(location);
        if (needUpdateChunk) {
            updateChunk(new ChunkCoords(location.getChunk()));
        }
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

    /**
     * @return the nmsHandler
     */
    public NMSHandler getNMSHandler() {
        return nmsHandler;
    }

    public static EditorManager getEditorManager() {
        return editor;
    }
}