package ykt.BeYkeRYkt.LightSource.sources;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.tasks.SendChunkTask;

public class SourceManager {

    private LightSource plugin;
    private int taskId;

    private List<Source> sources;

    public SourceManager(LightSource plugin) {
        this.plugin = plugin;
        this.sources = new ArrayList<Source>();
    }

    public void init() {
        this.taskId = Bukkit.getScheduler().runTaskTimer(plugin, new SendChunkTask(this), 0L, plugin.getDB().getTaskTicks()).getTaskId();
    }

    public boolean addSource(Source source) {
        if (!sources.isEmpty()) {
            for (Source i : sources) {
                if (!i.equals(source)) {
                    sources.add(source);
                    return true;
                }
            }
        } else {
            sources.add(source);
        }

        return false;
    }

    public synchronized boolean removeSource(Source source) {
        if (!sources.isEmpty()) {
            for (Source i : sources) {
                if (i.equals(source)) {
                    sources.remove(source);
                    return true;
                }
            }
        } else {
            sources.remove(source);
        }
        return false;
    }

    public Source getSource(Entity entity) {
        if (!sources.isEmpty()) {
            for (Source i : sources) {
                if (i.getOwner().getEntityId() == entity.getEntityId()) {
                    return i;
                }
            }
        }
        return null;
    }

    public List<Source> getSourceList() {
        return sources;
    }

    public int getTaskID() {
        return taskId;
    }
}