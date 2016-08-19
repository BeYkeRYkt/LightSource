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

import org.bukkit.Location;

public abstract class Source implements AutoCloseable {

	private Location old;
	private Location loc;
	private int lightLevel;

	public Source(Location location, int lightLevel) {
		this.old = location;
		this.loc = location;
		setLightLevel(lightLevel);
	}

	public Location getLocation() {
		return loc;
	}

	public Location getOldLocation() {
		return old;
	}

	public void setLocation(Location loc) {
		this.old = getLocation();
		this.loc = loc;
	}

	public int getLightLevel() {
		return lightLevel;
	}

	public void setLightLevel(int lightLevel) {
		this.lightLevel = lightLevel;
	}

	public abstract boolean shouldExecute();

	public abstract void onUpdate();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lightLevel;
		result = prime * result + ((loc == null) ? 0 : loc.hashCode());
		result = prime * result + ((old == null) ? 0 : old.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Source)) {
			return false;
		}
		Source other = (Source) obj;
		if (lightLevel != other.lightLevel) {
			return false;
		}
		if (loc == null) {
			if (other.loc != null) {
				return false;
			}
		} else if (!loc.equals(other.loc)) {
			return false;
		}
		if (old == null) {
			if (other.old != null) {
				return false;
			}
		} else if (!old.equals(other.old)) {
			return false;
		}
		return true;
	}

	@Override
	public void close() throws Exception {
		// System.out.println("Closing!");
	}
}
