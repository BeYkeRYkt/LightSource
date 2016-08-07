package ru.beykerykt.lightsource.items.flags;

import ru.beykerykt.lightsource.sources.ItemableSource;

public interface TickableFlagExecutor extends FlagExecutor {

	public void onTick(ItemableSource source, String[] args);

}
