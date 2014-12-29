package ykt.BeYkeRYkt.LightSource.api.editor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Editor {

    private String name;
    private int stage;

    public Editor(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
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

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(name);
    }
}