package ru.beykerykt.lightsource.items.flags;

import ru.beykerykt.lightsource.sources.ItemableSource;

public interface EndingFlagExecutor extends FlagExecutor {

	public void onEnd(ItemableSource source, String[] args);

}
