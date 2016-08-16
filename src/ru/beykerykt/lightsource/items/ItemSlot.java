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
package ru.beykerykt.lightsource.items;

public enum ItemSlot {
	HELMET,
	CHESTPLATE,
	LEGGINGS,
	BOOTS,
	LEFT_HAND,
	RIGHT_HAND;

	public static ItemSlot getItemSlotFromArmorContent(int i) {
		switch (i) {
			case 0:
				return ItemSlot.BOOTS;
			case 1:
				return ItemSlot.LEGGINGS;
			case 2:
				return ItemSlot.CHESTPLATE;
			case 3:
				return ItemSlot.HELMET;
			case 4:
				return ItemSlot.LEFT_HAND;
			case 5:
				return ItemSlot.RIGHT_HAND;
		}
		return null;
	}

	public static int getArmorContentFromItemSlot(ItemSlot slot) {
		switch (slot) {
			case HELMET:
				return 3;
			case CHESTPLATE:
				return 2;
			case LEGGINGS:
				return 1;
			case BOOTS:
				return 0;
			case LEFT_HAND:
				return 4;
			case RIGHT_HAND:
				return 5;
		}
		return 0;
	}
}
