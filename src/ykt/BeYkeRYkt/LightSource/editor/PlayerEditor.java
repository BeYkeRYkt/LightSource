package ykt.BeYkeRYkt.LightSource.editor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.LightSource.items.LightItem;

public class PlayerEditor {

    private String name;
    private int stage;
    private LightItem changeItem;

    public PlayerEditor(String name, LightItem item) {
        this.name = name;
        this.changeItem = item;
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(name);
    }

    /**
     * @return the stage
     */
    public int getStage() {
        return stage;
    }

    /**
     * @param stage
     *            the stage to set
     */
    public void setStage(int stage) {
        this.stage = stage;
    }

    public LightItem getItem() {
        return changeItem;
    }
}