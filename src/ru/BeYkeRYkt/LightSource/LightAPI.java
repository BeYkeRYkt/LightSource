package ru.BeYkeRYkt.LightSource;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import ru.BeYkeRYkt.LightSource.events.DeleteLightEvent;
import ru.BeYkeRYkt.LightSource.events.SetLightEvent;
import ru.BeYkeRYkt.LightSource.gui.GUIManager;
import ru.BeYkeRYkt.LightSource.gui.editor.EditorManager;
import ru.BeYkeRYkt.LightSource.items.ItemManager;
import ru.BeYkeRYkt.LightSource.nms.NMSHandler;
import ru.BeYkeRYkt.LightSource.nms.NMSHandler_v_1_7_10;
import ru.BeYkeRYkt.LightSource.nms.NMSHandler_v_1_8;
import ru.BeYkeRYkt.LightSource.sources.ChunkCoords;
import ru.BeYkeRYkt.LightSource.sources.SourceManager;
import ru.BeYkeRYkt.LightSource.task.TaskManager;

public class LightAPI {

    private static ItemManager manager;
    private GUIManager gui;
    private static SourceManager source;
    public static CommandSender BUKKIT_SENDER = Bukkit.getConsoleSender();
    private static NMSHandler nmsHandler;
    private static EditorManager editor;
    private static TaskManager task;
    private static String version;

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
            source = new SourceManager();
            task = new TaskManager();
            gui = new GUIManager();
            editor = new EditorManager();
        }
    }

    public void init() {
        manager.loadItems();
        editor.init();
        task.init();
        gui.load();
        nmsHandler.initWorlds();
    }

    public static void createLight(Location location, int lightlevel) {
        SetLightEvent event = new SetLightEvent(location, lightlevel);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled())
            return;
        nmsHandler.createLight(event.getLocation(), event.getLightLevel());
    }

    public static void deleteLight(Location location, boolean needUpdateChunk) {

        DeleteLightEvent event = new DeleteLightEvent(location);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled())
            return;
        nmsHandler.deleteLight(event.getLocation());
        if (needUpdateChunk) {
            updateChunk(new ChunkCoords(event.getLocation().getChunk()));
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

    public static TaskManager getTaskManager() {
        return task;
    }

    public static String getVersion() {
        return version;
    }

}