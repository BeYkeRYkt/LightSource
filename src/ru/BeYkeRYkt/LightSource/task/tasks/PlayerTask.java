package ru.BeYkeRYkt.LightSource.task.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.EntityType;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.sources.Source;
import ru.BeYkeRYkt.LightSource.sources.SourceManager;
import ru.BeYkeRYkt.LightSource.task.Task;

public class PlayerTask extends Task {

    private int iteratorCount = 0;
    private int maxIterationsPerTick;
    private SourceManager manager;

    private List<Source> sources;

    @Override
    public void start() {
        super.start();
        this.manager = LightSource.getInstance().getSourceManager();
        this.sources = new ArrayList<Source>();
        this.maxIterationsPerTick = LightSource.getInstance().getDB().getMaxIterationsPerTick();
    }

    @Override
    public String getId() {
        return "main_PlayerTask";
    }

    @Override
    public void doTick() {
        iteratorCount = 0;

        while (!this.sources.isEmpty() && iteratorCount < maxIterationsPerTick) {
            iteratorCount++;
            Source source = sources.get(0);
            source.doTick();
            sources.remove(0);
        }

        if (sources.isEmpty()) {
            addSources();
        }

    }

    public void addSources() {
        for (Source source : manager.getSourceList()) {
            if (source.getOwner().getType() == EntityType.PLAYER) {
                sources.add(source);
            }
        }
    }
}