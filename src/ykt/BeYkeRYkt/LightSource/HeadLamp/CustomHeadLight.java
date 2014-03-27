package ykt.BeYkeRYkt.LightSource.HeadLamp;

import org.bukkit.Material;

public class CustomHeadLight {

	private String name;
	private Material material;
	private int light;

	public CustomHeadLight(String name, Material material, int lightlevel) {
		this.name = name;
		this.material = material;
		this.light = lightlevel;
	}

	// Имя
	public String getName() {
		return name;
	}

	// Материал
	public Material getMaterial() {
		return material;
	}

	// Уровень света
	public int getLevelLight() {
		return light;
	}
}