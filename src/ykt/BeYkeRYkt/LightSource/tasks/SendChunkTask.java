package ykt.BeYkeRYkt.LightSource.tasks;

import java.util.ArrayList;
import java.util.List;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.sources.Source;
import ykt.BeYkeRYkt.LightSource.sources.SourceManager;

public class SendChunkTask implements Runnable {

	private int iteratorCount = 0;
	private int maxIterationsPerTick;
	private SourceManager manager;

	private List<Source> sources;

	public SendChunkTask(SourceManager sourceManager) {
		this.manager = sourceManager;
		this.sources = new ArrayList<Source>(this.manager.getSourceList());
		this.maxIterationsPerTick = LightSource.getInstance().getDB().getMaxIterationsPerTick();
	}

	public List<Source> getSources() {
		return sources;
	}

	@Override
	public void run() {
		iteratorCount = 0;

		while (!this.sources.isEmpty() && iteratorCount < maxIterationsPerTick) {
			iteratorCount++;
			Source source = sources.get(0);
			source.doBurnTick();
			source.doTick();
			LightAPI.updateChunk(source.getChunk());
			sources.remove(0);
		}

		if (sources.isEmpty()) {
			sources.addAll(manager.getSourceList());
		}

	}

}