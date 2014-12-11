package ykt.BeYkeRYkt.LightSource;

import org.bukkit.configuration.file.FileConfiguration;

public class LSConfig {

	/**
	 * Enum for fast configuration
	 */
	public enum UpdateMode {
		/**
		 * player light : true
		 * entity light: false
		 * item light: false
		 * Task delay: 20
		 * max-iterations-per-tick: 2
		 */
		SAVE,

		/**
		 * player light : true
		 * entity light: true
		 * item light: true
		 * Task delay: 4
		 * max-iterations-per-tick: 5
		 */
		MAXIMUM,

		/**
		 * player light : true
		 * entity light: false
		 * item light: false
		 * Task delay: 15
		 * max-iterations-per-tick: 3
		 */
		USER;
	}

	private LightSource plugin;
	private boolean playerlight;
	private boolean entitylight;
	private boolean itemlight;

	// private boolean updater;
	private int taskticks;
	private int maxIterationsPerTick;

	private UpdateMode strategyUpdate = UpdateMode.USER;

	public LSConfig(LightSource plugin) {
		this.plugin = plugin;

		String mode = plugin.getConfig().getString("LightUpdateMode");
		if (UpdateMode.valueOf(mode) != null) {
			strategyUpdate = UpdateMode.valueOf(mode);
		}

		if (strategyUpdate == UpdateMode.SAVE) {
			setPlayerLight(true);
			setEntityLight(false);
			setItemLight(false);
			setTaskTicks(20);
			setMaxIterationsPerTick(2);
		} else if (strategyUpdate == UpdateMode.MAXIMUM) {
			setPlayerLight(true);
			setEntityLight(true);
			setItemLight(true);
			setTaskTicks(4);
			setMaxIterationsPerTick(5);
		} else {
			setPlayerLight(plugin.getConfig().getBoolean("PlayerLight"));
			setEntityLight(plugin.getConfig().getBoolean("EntityLight"));
			setItemLight(plugin.getConfig().getBoolean("ItemLight"));
			// setUpdater(plugin.getConfig().getBoolean("Enable-updater"));
			setTaskTicks(plugin.getConfig().getInt("Task-delay-ticks"));
			setMaxIterationsPerTick(plugin.getConfig().getInt("max-iterations-per-tick"));
		}
	}

	public void save() {
		FileConfiguration fc = plugin.getConfig();
		fc.set("PlayerLight", isPlayerLight());
		fc.set("EntityLight", isEntityLight());
		fc.set("ItemLight", isItemLight());
		// fc.set("Enable-updater", isUpdater());
		fc.set("Task-delay-ticks", getTaskTicks());
		fc.set("max-iterations-per-tick", getMaxIterationsPerTick());
		plugin.saveConfig();
	}

	public UpdateMode getMode() {
		return strategyUpdate;
	}

	public void setMode(UpdateMode mode) {
		this.strategyUpdate = mode;
	}

	/**
	 * @return the playerlight
	 */
	public boolean isPlayerLight() {
		return playerlight;
	}

	/**
	 * @param playerlight
	 *            the playerlight to set
	 */
	public void setPlayerLight(boolean playerlight) {
		this.playerlight = playerlight;
	}

	/**
	 * @return the entitylight
	 */
	public boolean isEntityLight() {
		return entitylight;
	}

	/**
	 * @param entitylight
	 *            the entitylight to set
	 */
	public void setEntityLight(boolean entitylight) {
		this.entitylight = entitylight;
	}

	/**
	 * @return the itemlight
	 */
	public boolean isItemLight() {
		return itemlight;
	}

	/**
	 * @param itemlight
	 *            the itemlight to set
	 */
	public void setItemLight(boolean itemlight) {
		this.itemlight = itemlight;
	}

	public boolean getWorld(String name) {
		return plugin.getConfig().getBoolean("Worlds." + name);
	}

	public void setWorld(String name, boolean flag) {
		plugin.getConfig().set("Worlds." + name, flag);
		plugin.saveConfig();
	}

	/**
	 * @return the taskticks
	 */
	public int getTaskTicks() {
		return taskticks;
	}

	/**
	 * @param taskticks
	 *            the taskticks to set
	 */
	public void setTaskTicks(int taskticks) {
		this.taskticks = taskticks;
	}

	/**
	 * @return the maxIterationsPerTick
	 */
	public int getMaxIterationsPerTick() {
		return maxIterationsPerTick;
	}

	/**
	 * @param maxIterationsPerTick
	 *            the maxIterationsPerTick to set
	 */
	public void setMaxIterationsPerTick(int maxIterationsPerTick) {
		this.maxIterationsPerTick = maxIterationsPerTick;
	}
}