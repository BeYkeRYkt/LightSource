package ykt.BeYkeRYkt.LightSource.api.items;

import org.bukkit.Material;

public class LightItem {

    private String id;
    private String name;
    private Material material;
    private int maxLight;
    private int maxBurnTime;

    public LightItem(String id, String name, Material material, int lightlevel, int burnTime) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.maxBurnTime = burnTime;
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
        return new LightItem(id, name, material, maxLight, maxBurnTime);
    }

    public void check() {
        // checker
        LightItem item = ItemManager.getLightItem(getId());

        if (item.getMaxLevelLight() != getMaxLevelLight()) {
            setMaxLevelLight(item.getMaxLevelLight());
        }

        if (item.getMaxBurnTime() != getMaxBurnTime()) {
            setMaxBurnTime(item.getMaxBurnTime());
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

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    public void setMaxBurnTime(int time) {
        this.maxBurnTime = time;
    }
}