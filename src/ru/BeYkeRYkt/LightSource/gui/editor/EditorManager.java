package ru.BeYkeRYkt.LightSource.gui.editor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import ru.BeYkeRYkt.LightSource.LightSource;
import ru.BeYkeRYkt.LightSource.items.ItemManager;
import ru.BeYkeRYkt.LightSource.items.LightItem;

public class EditorManager {

	private List<PlayerEditor> editors;
	private List<PlayerCreator> creators;

	private List<LightItem> cachedList;
	private int pages;

	public void init() {
		this.editors = new ArrayList<PlayerEditor>();
		this.creators = new ArrayList<PlayerCreator>();
		this.cachedList = new ArrayList<LightItem>(ItemManager.getList());
		this.pages = (cachedList.size() / 47) + 1;
	}

	public int getPages() {
		return pages;
	}

	public List<PlayerEditor> getEditors() {
		return editors;
	}

	public boolean addEditor(PlayerEditor editor) {
		if (!editors.isEmpty()) {
			for (PlayerEditor i : editors) {
				if (!i.equals(editor)) {
					editors.add(editor);
					return true;
				}
			}
		} else {
			editors.add(editor);
		}

		return false;
	}

	public PlayerEditor getEditor(String name) {
		for (PlayerEditor editor : editors) {
			if (editor.getBukkitPlayer().getName().equals(name)) {
				return editor;
			}
		}
		return null;
	}

	public boolean isEditor(String name) {
		for (PlayerEditor editor : editors) {
			if (editor.getBukkitPlayer().getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public synchronized boolean removeEditor(PlayerEditor editor) {
		if (!editors.isEmpty()) {
			for (PlayerEditor i : editors) {
				if (i.equals(editor)) {
					editors.remove(editor);
					return true;
				}
			}
		} else {
			editors.remove(editor);
		}
		return false;
	}

	public List<PlayerCreator> getCreators() {
		return creators;
	}

	public boolean addCreator(PlayerCreator creator) {
		if (!creators.isEmpty()) {
			for (PlayerCreator i : creators) {
				if (!i.equals(creator)) {
					creators.add(creator);
					return true;
				}
			}
		} else {
			creators.add(creator);
		}

		return false;
	}

	public PlayerCreator getCreator(String name) {
		for (PlayerCreator creator : creators) {
			if (creator.getBukkitPlayer().getName().equals(name)) {
				return creator;
			}
		}
		return null;
	}

	public boolean isCreator(String name) {
		for (PlayerCreator creator : creators) {
			if (creator.getBukkitPlayer().getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public synchronized boolean removeCreator(PlayerCreator creator) {
		if (!creators.isEmpty()) {
			for (PlayerCreator i : creators) {
				if (i.equals(creator)) {
					creators.remove(creator);
					return true;
				}
			}
		} else {
			creators.remove(creator);
		}
		return false;
	}

	/**
	 * @return the cachedList
	 */
	public List<LightItem> getCachedItemsList() {
		return cachedList;
	}

	public void refreshCachedItemsList() {
		this.cachedList = new ArrayList<LightItem>(ItemManager.getList());
	}

	public void save() {
		if (!editors.isEmpty()) {
			for (PlayerEditor editor : editors) {
				editor.getBukkitPlayer().closeInventory();
			}
		}
		editors.clear();

		if (!creators.isEmpty()) {
			for (PlayerCreator creator : creators) {
				creator.getBukkitPlayer().closeInventory();
			}
		}
		creators.clear();

		FileConfiguration config = LightSource.getInstance().getItemManager().getConfig();
		for (LightItem item : ItemManager.getList()) {
			config.set(item.getId() + ".name", item.getName());
			config.set(item.getId() + ".data", item.getData());
			config.set(item.getId() + ".material", item.getMaterial().name());
			config.set(item.getId() + ".lightlevel", item.getMaxLevelLight());
		}
		LightSource.getInstance().getItemManager().saveConfig();
	}
}
