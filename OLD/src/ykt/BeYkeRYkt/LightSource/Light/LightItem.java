package ykt.BeYkeRYkt.LightSource.Light;

import org.bukkit.Material;

public class LightItem {

	private String name;
	private Material material;
	private int light;

	public LightItem(String name, Material material, int lightlevel) {
		this.name = name;
		this.material = material;
		this.light = lightlevel;
	}

	//Name
	public String getName() {
		return name;
	}

	//Material
	public Material getMaterial() {
		return material;
	}

	//LightLevel
	public int getLevelLight() {
		return light;
	}
}