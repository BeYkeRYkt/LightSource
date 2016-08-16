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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.Entity;

public class SourceManager {

	private List<Source> sources;

	public SourceManager() {
		this.sources = new CopyOnWriteArrayList<Source>();
	}

	public boolean addSource(Source source) {
		if (sources.contains(source))
			return false;
		sources.add(source);
		return true;
	}

	public boolean removeSource(Source source) {
		if (!sources.contains(source))
			return false;
		sources.remove(source);
		return true;
	}

	public Source getSource(Entity entity) {
		for (Source source : getSourceList()) {
			if (source instanceof OwnedSource) {
				OwnedSource os = (OwnedSource) source;
				if (os.getOwner().getUniqueId().equals(entity.getUniqueId())) {
					return source;
				}
			}
		}
		return null;
	}

	public boolean isSource(Entity entity) {
		for (Source source : getSourceList()) {
			if (source instanceof OwnedSource) {
				OwnedSource os = (OwnedSource) source;
				if (os.getOwner().getUniqueId().equals(entity.getUniqueId())) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Source> getSourceList() {
		return sources;
	}
}
