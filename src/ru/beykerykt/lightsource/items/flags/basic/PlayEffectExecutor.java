/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 - 2016
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.beykerykt.lightsource.items.flags.basic;

import org.bukkit.Effect;

import ru.beykerykt.lightsource.items.flags.TickableFlagExecutor;
import ru.beykerykt.lightsource.sources.ItemableSource;

public class PlayEffectExecutor implements TickableFlagExecutor {

	@Override
	public void onTick(ItemableSource source, String[] args) {
		if (args.length >= getMaxArgs()) {
			String effectName = args[0];
			int data = Integer.parseInt(args[1]);
			int count = Integer.parseInt(args[2]);
			source.getLocation().getWorld().spigot().playEffect(source.getLocation(), Effect.valueOf(effectName.toUpperCase()), 0, data, 0, 0, 0, 0, count, 10);
		} else {
			// default ?
			source.getLocation().getWorld().spigot().playEffect(source.getLocation(), Effect.PARTICLE_SMOKE, 0, 0, 0, 0, 0, 0, 1, 10);
		}
	}

	@Override
	public String getDescription() {
		return "play_effect:[effect name]:[data]:[count]";
	}

	@Override
	public int getMaxArgs() {
		return 3;
	}

}
