package ru.beykerykt.lightsource.items;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ru.beykerykt.lightsource.LightSourceAPI;
import ru.beykerykt.lightsource.items.flags.EndingFlagExecutor;
import ru.beykerykt.lightsource.items.flags.FlagExecutor;
import ru.beykerykt.lightsource.items.flags.RequirementFlagExecutor;
import ru.beykerykt.lightsource.items.flags.TickableFlagExecutor;
import ru.beykerykt.lightsource.sources.Source;

public class Item {

	private String id;
	private String displayName;
	private Material material;
	private int levelLight;
	private int data;
	private List<String> flags;

	public Item(String id, Material material, int data, int lightlevel) {
		this.id = id;
		this.material = material;
		this.levelLight = lightlevel;
		this.data = data;
		this.flags = new CopyOnWriteArrayList<String>();
	}

	public String getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String name) {
		this.displayName = name;
	}

	public Material getMaterial() {
		return material;
	}

	public int getLevelLight() {
		return levelLight;
	}

	public void setLevelLight(int level) {
		this.levelLight = level;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public List<String> getFlagsList() {
		return flags;
	}

	public void setFlagsList(List<String> flags) {
		this.flags = flags;
	}

	public boolean callRequirementFlags(Entity entity, ItemStack itemStack) {
		for (String flag : getFlagsList()) {
			String[] args = flag.split(":").clone();
			FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0]);
			args = (String[]) ArrayUtils.remove(args, 0);
			if (executor instanceof RequirementFlagExecutor) {
				RequirementFlagExecutor rfe = (RequirementFlagExecutor) executor;
				if (!rfe.onCheckRequirement(entity, itemStack, this, args)) {
					return false;
				}
			}
		}
		return true;
	}

	public void callUpdateFlag(Source source) {
		for (String flag : getFlagsList()) {
			String[] args = flag.split(":").clone();
			FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0]);
			args = (String[]) ArrayUtils.remove(args, 0);
			if (executor instanceof TickableFlagExecutor) {
				TickableFlagExecutor tfe = (TickableFlagExecutor) executor;
				tfe.onTick(source, args);
			}
		}
	}

	public void callEndingFlag(Source source) {
		for (String flag : getFlagsList()) {
			String[] args = flag.split(":").clone();
			FlagExecutor executor = LightSourceAPI.getFlagManager().getFlag(args[0]);
			args = (String[]) ArrayUtils.remove(args, 0);
			if (executor instanceof EndingFlagExecutor) {
				EndingFlagExecutor efe = (EndingFlagExecutor) executor;
				efe.onEnd(source, args);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (data != other.data) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (material != other.material) {
			return false;
		}
		return true;
	}
}
