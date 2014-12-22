package ykt.BeYkeRYkt.LightSource.editor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import ykt.BeYkeRYkt.LightSource.LightAPI;
import ykt.BeYkeRYkt.LightSource.items.ItemManager;
import ykt.BeYkeRYkt.LightSource.items.LightItem;

public class EditorManager {

    private List<PlayerEditor> editors;
    private List<LightItem> cachedList;
    private int pages;

    public void init() {
        this.editors = new ArrayList<PlayerEditor>();
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

    /**
     * @return the cachedList
     */
    public List<LightItem> getCachedItemsList() {
        return cachedList;
    }

    public void save() {
        if (!editors.isEmpty()) {
            for (PlayerEditor editor : editors) {
                editor.getBukkitPlayer().closeInventory();
            }
        }
        editors.clear();
        FileConfiguration config = LightAPI.getItemManager().getConfig();
        for (LightItem item : ItemManager.getList()) {
            config.set(item.getId() + ".name", item.getName());
            config.set(item.getId() + ".lightlevel", item.getMaxLevelLight());
            config.set(item.getId() + ".burnTime", item.getMaxBurnTime());
        }
        LightAPI.getItemManager().saveConfig();
    }
}