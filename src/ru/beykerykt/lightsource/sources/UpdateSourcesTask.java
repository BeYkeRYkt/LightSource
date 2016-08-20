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
package ru.beykerykt.lightsource.sources;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.flags.FlagHelper;

public class UpdateSourcesTask implements Runnable {

	private boolean isStarted;
	private ScheduledFuture<?> sch;

	public void start(int ticks) {
		if (!isStarted) {
			isStarted = true;
			sch = LightSourceAPI.getSchedulerExecutor().scheduleWithFixedDelay(this, 0, 50 * ticks, TimeUnit.MILLISECONDS);
		}
	}

	public void shutdown() {
		if (isStarted) {
			isStarted = false;
			sch.cancel(false);
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < LightSourceAPI.getSourceManager().getSourceList().size(); i++) {
			Source source = LightSourceAPI.getSourceManager().getSourceList().get(i);
			if (source == null)
				break;
			if (source.shouldExecute()) {
				source.onUpdate();
			} else {
				if (source instanceof ItemableSource) {
					FlagHelper.callEndingFlag((ItemableSource) source);
				}
				LightSourceAPI.getSourceManager().getSourceList().remove(i);
			}
		}
	}
}
