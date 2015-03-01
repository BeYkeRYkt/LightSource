package ru.BeYkeRYkt.LightSource.items;

import org.bukkit.Material;

public class LightItem {

    private String id;
    private String name;
    private Material material;
    private int maxLight;
    private int data;

    public LightItem(String id, String name, Material material, int data, int lightlevel) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.data = data;
        this.maxLight = lightlevel;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public LightItem clone() {
        return new LightItem(id, name, material, data, maxLight);
    }

    public void check() {
        // checker
        LightItem item = ItemManager.getLightItem(getId());

        if (item != null) {
            if (item.getMaxLevelLight() != getMaxLevelLight()) {
                setMaxLevelLight(item.getMaxLevelLight());
            }
        }
    }

    public int getMaxLevelLight() {
        return maxLight;
    }

    public void setMaxLevelLight(int level) {
        this.maxLight = level;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}