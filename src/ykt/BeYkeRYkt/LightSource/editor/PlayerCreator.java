package ykt.BeYkeRYkt.LightSource.editor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerCreator {

    private String name;
    private int stage;

    // for item
    private String id;
    private Material material;

    public PlayerCreator(String name) {
        this.name = name;
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

    /**
     * @return the id
     */
    public String getID() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material
     *            the material to set
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof PlayerCreator)) {
            return false;
        } else {
            PlayerCreator creator = (PlayerCreator) other;
            return getBukkitPlayer().getName().equals(creator.getBukkitPlayer().getName());
        }
    }
}