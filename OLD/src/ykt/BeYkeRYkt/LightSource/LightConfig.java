package ykt.BeYkeRYkt.LightSource;

import org.bukkit.configuration.file.FileConfiguration;


public class LightConfig{
	
	private boolean playerlight;
	private boolean entitylight;
	private boolean itemlight;
	
	private boolean updater;
	private int taskticks;
	
	public LightConfig(){
		setPlayerLight(LightSource.getInstance().getConfig().getBoolean("PlayerLight"));
		setEntityLight(LightSource.getInstance().getConfig().getBoolean("EntityLight"));
		setItemLight(LightSource.getInstance().getConfig().getBoolean("ItemLight"));
		setUpdater(LightSource.getInstance().getConfig().getBoolean("Enable-updater"));
		setTaskTicks(LightSource.getInstance().getConfig().getInt("Task-delay-ticks"));
	}

	public void save(){
		FileConfiguration fc = LightSource.getInstance().getConfig();
		fc.set("PlayerLight", isPlayerLight());
		fc.set("EntityLight", isEntityLight());
		fc.set("ItemLight", isItemLight());
		fc.set("Enable-updater", isUpdater());
		fc.set("Task-delay-ticks", getTaskTicks());
		LightSource.getInstance().saveConfig();
	}

	/**
	 * @return the playerlight
	 */
	public boolean isPlayerLight() {
		return playerlight;
	}

	/**
	 * @param playerlight the playerlight to set
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
	 * @param entitylight the entitylight to set
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
	 * @param itemlight the itemlight to set
	 */
	public void setItemLight(boolean itemlight) {
		this.itemlight = itemlight;
	}

	/**
	 * @return the updater
	 */
	public boolean isUpdater() {
		return updater;
	}

	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(boolean updater) {
		this.updater = updater;
	}
	
	public boolean getWorld(String name){
		return LightSource.getInstance().getConfig().getBoolean("Worlds." + name);
	}
	
	public void setWorld(String name, boolean flag){
		LightSource.getInstance().getConfig().set("Worlds." + name, flag);
		LightSource.getInstance().saveConfig();
	}

	/**
	 * @return the taskticks
	 */
	public int getTaskTicks() {
		return taskticks;
	}

	/**
	 * @param taskticks the taskticks to set
	 */
	public void setTaskTicks(int taskticks) {
		this.taskticks = taskticks;
	}
}