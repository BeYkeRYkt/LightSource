package ru.BeYkeRYkt.LightSource.task.tasks;

import java.util.ArrayList;
import java.util.List;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.sources.Source;
import ru.BeYkeRYkt.LightSource.sources.Source.ItemType;
import ru.BeYkeRYkt.LightSource.sources.SourceManager;
import ru.BeYkeRYkt.LightSource.task.Task;

public class ItemTask extends Task {

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
		return "main_itemTask";
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
			if (source.getType() == ItemType.ITEM) {
				sources.add(source);
			}
		}
	}
}
