package ykt.BeYkeRYkt.LightSource.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.LightUtils;
import ykt.BeYkeRYkt.LightSource.gui.icons.About;
import ykt.BeYkeRYkt.LightSource.gui.icons.Back;
import ykt.BeYkeRYkt.LightSource.gui.icons.EntityLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.ItemLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.Items;
import ykt.BeYkeRYkt.LightSource.gui.icons.PlayerLight;
import ykt.BeYkeRYkt.LightSource.gui.icons.Worlds;
import ykt.BeYkeRYkt.LightSource.gui.menus.MainMenu;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;
import ykt.BeYkeRYkt.LightSource.sources.PlayerSource;
import ykt.BeYkeRYkt.LightSource.sources.Source.ItemType;

/**
 * 
 * Port from GlowLib
 * 
 * @author GlowSparkletBox
 *
 */
public class GUIManager implements Listener {

	private List<Menu> menus = new ArrayList<Menu>();
	private List<Icon> icons = new ArrayList<Icon>();

	public GUIManager(LightSource plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public void load() {
		// Icons
		// getGUIManager().registerIcon(new NullLOL());
		LightSource.getAPI().getGUIManager().registerIcon(new About());
		LightSource.getAPI().getGUIManager().registerIcon(new Back());
		LightSource.getAPI().getGUIManager().registerIcon(new EntityLight());
		LightSource.getAPI().getGUIManager().registerIcon(new ItemLight());
		LightSource.getAPI().getGUIManager().registerIcon(new Items());
		LightSource.getAPI().getGUIManager().registerIcon(new PlayerLight());
		LightSource.getAPI().getGUIManager().registerIcon(new Worlds());

		// Menus
		LightSource.getAPI().getGUIManager().registerMenu(new MainMenu());
	}

	public Inventory openMenu(Player player, Menu menu) {
		Inventory inv = Bukkit.createInventory(null, menu.getSlots(), menu.getName());
		// init icons
		if (!menu.getIcons().isEmpty()) {
			for (Icon icon : menu.getIcons().keySet()) {
				int num = menu.getIcons().get(icon);
				ItemStack item = icon.getItemStack();
				inv.setItem(num, item);
			}
		}
		player.openInventory(inv);
		return inv;
	}

	public void registerMenu(Menu menu) {
		if (menu == null)
			return;
		if (!isMenu(menu.getName())) {
			menus.add(menu);
		}
	}

	public void registerIcon(Icon icon) {
		if (icon == null)
			return;
		if (!isIcon(icon.getItemStack())) {
			icons.add(icon);
		}
	}

	@Deprecated
	public void unregisterMenu(Menu menu) {
	}

	@Deprecated
	public void unregisterIcon(Icon icon) {
	}

	public Menu getMenuFromId(String id) {
		for (Menu menu : menus) {
			if (menu.getId().equals(id)) {
				return menu;
			}
		}
		return null;
	}

	public Menu getMenuFromName(String name) {
		for (Menu menu : menus) {
			if (menu.getName().equals(name)) {
				return menu;
			}
		}
		return null;
	}

	public Icon getIconFromId(String id) {
		for (Icon icon : icons) {
			if (icon.getId().equals(id)) {
				return icon;
			}
		}
		return null;
	}

	public Icon getIconFromName(String name) {
		for (Icon icon : icons) {
			if (icon.getName().equals(name)) {
				return icon;
			}
		}
		return null;
	}

	public boolean isMenu(String nameInv) {
		if (nameInv == null)
			return false;
		for (Menu menu : menus) {
			if (menu.getName().equals(nameInv)) {
				return true;
			}
		}
		return false;
	}

	public boolean isIcon(ItemStack item) {
		if (item == null)
			return false;
		for (Icon icon : icons) {
			if (icon.getMaterial() == item.getType()) {
				if (!item.getItemMeta().hasDisplayName())
					return false;
				if (icon.getName().equals(item.getItemMeta().getDisplayName())) {
					return true;
				}
			}
		}
		return false;
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		Inventory inventory = event.getInventory();

		if (inventory.getTitle() == null)
			return;
		if (isMenu(inventory.getTitle())) {
			Menu menu = getMenuFromName(inventory.getTitle());
			menu.onOpenMenu(event);
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Inventory inventory = event.getInventory();

		if (inventory.getTitle() == null)
			return;
		if (isMenu(inventory.getTitle())) {
			Menu menu = getMenuFromName(inventory.getTitle());
			menu.onCloseMenu(event);
		}
	}

	@EventHandler
	public void onItemClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();

		Inventory inventory = event.getInventory();

		if (item == null)
			return;
		if (inventory.getTitle() == null)
			return;
		if (isMenu(inventory.getTitle())) {
			if (isIcon(item)) {
				if (!item.getItemMeta().hasDisplayName())
					return;
				Icon icon = getIconFromName(item.getItemMeta().getDisplayName());
				icon.onItemClick(event);
			}
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemHeldChange(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItem(event.getNewSlot());
		Location loc = player.getLocation();

		if (item != null && ItemManager.isLightSource(item)) {
			LightItem lightItem = ItemManager.getLightItem(item);
			int time = (LightUtils.getFuelLore(item) * lightItem.getMaxBurnTime()) / 100;
			lightItem.setBurnTime(time, true);
			PlayerSource light = new PlayerSource(player, loc, lightItem, ItemType.HAND, item);
			light.updateLight(loc);
			LightAPI.getSourceManager().addSource(light);
		}
	}
}