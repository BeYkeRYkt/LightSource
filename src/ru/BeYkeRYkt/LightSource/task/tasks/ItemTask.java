package ru.BeYkeRYkt.LightSource.task.tasks;

import java.util.ArrayList;
import java.util.List;

import ru.BeYkeRYkt.LightSource.LightAPI;
import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.sources.ChunkCoords;
import ru.BeYkeRYkt.LightSource.sources.Source;
import ru.BeYkeRYkt.LightSource.sources.Source.ItemType;
import ru.BeYkeRYkt.LightSource.sources.SourceManager;
import ru.BeYkeRYkt.LightSource.task.Task;

public class ItemTask extends Task {

    private int iteratorCount = 0;
    private int maxIterationsPerTick;
    private SourceManager manager;

    private List<Source> sources;
    private List<ChunkCoords> chunks;

    @Override
    public void start() {
        super.start();
        this.manager = LightAPI.getSourceManager();
        this.sources = new ArrayList<Source>();
        this.maxIterationsPerTick = LightSource.getInstance().getDB().getMaxIterationsPerTick();
        this.chunks = new ArrayList<ChunkCoords>();
    }

    @Override
    public String getId() {
        return "main_itemTask";
    }

    @Override
    public void doTick() {
        iteratorCount = 0;

        while (!this.sources.isEmpty() && iteratorCount < maxIterationsPerTick) {
            iteratorCount++;
            Source source = sources.get(0);
            source.doBurnTick();
            source.doTick();

            if (!chunks.contains(source.getChunk())) {
                chunks.add(source.getChunk());
            }
            sources.remove(0);
        }

        while (!chunks.isEmpty()) {
            ChunkCoords chunk = chunks.get(0);
            LightAPI.updateChunk(chunk);
            chunks.remove(0);
        }

        if (sources.isEmpty()) {
            addSources();
        }

    }

    public void addSources() {
        for (Source source : manager.getSourceList()) {
            if (source.getType() == ItemType.ITEM) {
                sources.add(source);
            }
        }
    }
}