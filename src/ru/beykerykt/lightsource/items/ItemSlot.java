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
