package ykt.BeYkeRYkt.LightSource.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public abstract class Menu {

	private String id;
	private String name;
	private int slots;

	private Map<Icon, Integer> icons;

	public Menu(String id, String name, int slots) {
		this.id = id;
		this.name = name;
		this.slots = slots;
		this.icons = new HashMap<Icon, Integer>();
	}

	public void addItem(Icon icon, int slot) {
		// icons.add(slot - 1, icon);
		icons.put(icon, slot - 1);
	}

	public String getName() {
		return name;
	}

	public int getSlots() {
		return slots;
	}

	public String getId() {
		return id;
	}

	public abstract void onOpenMenu(InventoryOpenEvent event);

	public abstract void onCloseMenu(InventoryCloseEvent event);

	public Map<Icon, Integer> getIcons() {
		return icons;
	}
}