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
package ru.beykerykt.lightsource.sources.search;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class IgnoreEntityEntry implements AutoCloseable {
	private Entity entity;
	private ItemStack itemStack;

	public IgnoreEntityEntry(Entity entity, ItemStack itemStack) {
		this.entity = entity;
		this.itemStack = itemStack;
	}

	public Entity getEntity() {
		return entity;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((itemStack == null) ? 0 : itemStack.hashCode());
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
		if (!(obj instanceof IgnoreEntityEntry)) {
			return false;
		}
		IgnoreEntityEntry other = (IgnoreEntityEntry) obj;
		if (entity == null) {
			if (other.entity != null) {
				return false;
			}
		} else if (!entity.equals(other.entity)) {
			return false;
		}
		if (itemStack == null) {
			if (other.itemStack != null) {
				return false;
			}
		} else if (!itemStack.equals(other.itemStack)) {
			return false;
		}
		return true;
	}

	@Override
	public void close() {
	}

}
