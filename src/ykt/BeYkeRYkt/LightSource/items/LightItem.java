package ykt.BeYkeRYkt.LightSource.items;

import org.bukkit.Material;

import ykt.BeYkeRYkt.LightSource.LightSource;

public class LightItem implements Cloneable {

	private String id;
	private String name;
	private Material material;
	private int light;
	private int burnTime;
	private int maxBurnTime;

	private int ticks;
	private int maxTicks;

	public LightItem(String id, String name, Material material, int lightlevel, int burnTime) {
		this.maxTicks = 20 / LightSource.getInstance().getDB().getTaskTicks();

		this.id = id;
		this.name = name;
		this.material = material;
		this.burnTime = burnTime;
		this.maxBurnTime = burnTime;
		this.light = lightlevel;
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

	public int getLevelLight() {
		return light;
	}

	public void setLevelLight(int newLevel) {
		this.light = newLevel;
	}

	public int getBurnTime() {
		return this.burnTime;
	}

	/**
	 * @return the maxBurnTime
	 */
	public int getMaxBurnTime() {
		return maxBurnTime;
	}

	public void setBurnTime(int time, boolean updateLightLevel) {
		this.burnTime = time;

		if (updateLightLevel) {
			if (getPercent() >= 80 && getPercent() <= 90) {
				if (getLevelLight() > 13) {
					setLevelLight(13);
				}
			} else if (getPercent() >= 60 && getPercent() < 80) {
				if (getLevelLight() > 11) {
					setLevelLight(11);
				}
			} else if (getPercent() >= 40 && getPercent() < 60) {
				if (getLevelLight() > 9) {
					setLevelLight(9);
				}
			} else if (getPercent() >= 10 && getPercent() < 30) {
				if (getLevelLight() > 7) {
					setLevelLight(7);
				}
			} else if (getPercent() > 1 && getPercent() < 10) {
				if (getLevelLight() > 5) {
					setLevelLight(5);
				}
			} else if (getPercent() >= 0 && getPercent() < 1) {
				setLevelLight(0);
			}
		}
	}

	public int getPercent() {
		return (int) ((this.burnTime * 100) / maxBurnTime);
	}

	@Override
	public LightItem clone() {
		return new LightItem(id, name, material, light, burnTime);
	}

	public void burn() {
		if (burnTime > 0) {
			++ticks;
			if (ticks == maxTicks) {
				--burnTime;
				ticks = 0; // restart
			}
		}
	}

	public void setEnd() {
		burnTime = -2;
	}

	public boolean isEnded() {
		return burnTime == -2;
	}
}