package ru.BeYkeRYkt.LightSource.gui.editor;

import org.bukkit.Material;

public class PlayerCreator extends Editor {

    // for item
    private String id;
    private Material material;

    public PlayerCreator(String name) {
        super(name);
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