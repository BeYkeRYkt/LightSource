package ykt.BeYkeRYkt.LightSource.tasks;

import java.util.ArrayList;
import java.util.List;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.LightAPI;
import ykt.BeYkeRYkt.LightSource.api.sources.ChunkCoords;
import ykt.BeYkeRYkt.LightSource.api.sources.Source;
import ykt.BeYkeRYkt.LightSource.api.sources.SourceManager;
import ykt.BeYkeRYkt.LightSource.api.task.Task;

public class UpdateTask extends Task {

    private int iteratorCount = 0;
    private int maxIterationsPerTick;
    private SourceManager manager;

    private List<Source> sources;
    private List<ChunkCoords> chunks;

    @Override
    public void start() {
        super.start();
        this.manager = LightAPI.getSourceManager();
        this.sources = new ArrayList<Source>(this.manager.getSourceList());
        this.maxIterationsPerTick = LightSource.getInstance().getDB().getMaxIterationsPerTick();
        this.chunks = new ArrayList<ChunkCoords>();
    }

    @Override
    public void stop() {
        super.stop();
        // this.sources.clear();
        // this.chunks.clear();
    }

    @Override
    public String getId() {
        return "main_UpdateTask";
    }

    @Override
    public void doTick() {
        iteratorCount = 0;

        while (!this.sources.isEmpty() && iteratorCount < maxIterationsPerTick) {
            iteratorCount++;
            Source source = sources.get(0);
            source.doBurnTick();
            source.doTick();
            // LightAPI.updateChunk(source.getChunk());

            if (!chunks.contains(source.getChunk())) {
                chunks.add(source.getChunk());
            }
            sources.remove(0);
        }

        if (!chunks.isEmpty()) {
            ChunkCoords chunk = chunks.get(0);
            LightAPI.updateChunk(chunk);
            chunks.remove(0);
        }

        if (sources.isEmpty()) {
            sources.addAll(manager.getSourceList());
        }

    }

}