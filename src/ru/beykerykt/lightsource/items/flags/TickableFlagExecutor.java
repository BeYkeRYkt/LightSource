package ru.beykerykt.lightsource.items.flags;

import ru.beykerykt.lightsource.sources.Source;

public interface TickableFlagExecutor extends FlagExecutor {

	public void onTick(Source source, String[] args);

}
