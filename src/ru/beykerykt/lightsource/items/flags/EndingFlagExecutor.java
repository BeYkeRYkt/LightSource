package ru.beykerykt.lightsource.items.flags;

import ru.beykerykt.lightsource.sources.Source;

public interface EndingFlagExecutor extends FlagExecutor {

	public void onEnd(Source source, String[] args);

}
