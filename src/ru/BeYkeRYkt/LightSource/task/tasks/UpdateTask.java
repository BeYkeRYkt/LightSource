package ru.BeYkeRYkt.LightSource.task.tasks;

import java.util.ArrayList;
import java.util.List;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.sources.Source;
import ru.BeYkeRYkt.LightSource.sources.SourceManager;
import ru.BeYkeRYkt.LightSource.task.Task;

public class UpdateTask extends Task {

    private int iteratorCount = 0;
    private int maxIterationsPerTick;
    private SourceManager manager;

    private List<Source> sources;

    @Override
    public void start() {
        super.start();
        this.manager = LightSource.getInstance().getSourceManager();
        this.sources = new ArrayList<Source>(this.manager.getSourceList());
        this.maxIterationsPerTick = LightSource.getInstance().getDB().getMaxIterationsPerTick();
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
            source.doTick();
            sources.remove(0);
        }

        if (sources.isEmpty()) {
            sources.addAll(manager.getSourceList());
        }

    }

}