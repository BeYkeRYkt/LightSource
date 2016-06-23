package ru.beykerykt.lightsource.runnables;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.sources.Source;

public class UpdateSourcesTask implements Runnable {
	private int iteratorCount = 0;
	private int maxIterationsPerTick;

	public UpdateSourcesTask(int maxIterationsPerTicks) {
		this.maxIterationsPerTick = maxIterationsPerTicks;
	}

	@Override
	public void run() {
		// TODO: Implement iterations
		// while (!LightSourceAPI.getSourceManager().getSourceList().isEmpty() && iteratorCount < maxIterationsPerTick) {
		// iteratorCount++;
		// }
		for (Source source : LightSourceAPI.getSourceManager().getSourceList()) {
			if (source.shouldExecute()) {
				source.onUpdate();
			} else {
				source.getItem().callEndingFlag(source);
				LightSourceAPI.getSourceManager().removeSource(source);
			}
		}
	}
}
