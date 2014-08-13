package ykt.BeYkeRYkt.LightSource;

import org.bukkit.configuration.file.FileConfiguration;


public class LightConfig{
	
	private boolean playerlight;
	private boolean entitylight;
	private boolean itemlight;
	
	private boolean updater;
	private boolean debug;
	private int radius;
	private int delaystart;
	private int delayrestart;
	//private List<World> worlds;
	
	public LightConfig(){
		setPlayerLight(LightSource.getInstance().getConfig().getBoolean("PlayerLight"));
		setEntityLight(LightSource.getInstance().getConfig().getBoolean("EntityLight"));
		setItemLight(LightSource.getInstance().getConfig().getBoolean("ItemLight"));
		setUpdater(LightSource.getInstance().getConfig().getBoolean("Enable-updater"));
		setDebug(LightSource.getInstance().getConfig().getBoolean("Debug"));
		setRadius(LightSource.getInstance().getConfig().getInt("RadiusSendPackets"));
		setDelayStart(LightSource.getInstance().getConfig().getInt("Delay-before-starting-staggered-runnable-ticks"));
		setDelayRestart(LightSource.getInstance().getConfig().getInt("Delay-between-restarting-staggered-runnable-ticks"));
	}

	public void save(){
		FileConfiguration fc = LightSource.getInstance().getConfig();
		fc.set("PlayerLight", isPlayerLight());
		fc.set("EntityLight", isEntityLight());
		fc.set("ItemLight", isItemLight());
		fc.set("Enable-updater", isUpdater());
		fc.set("Debug", isDebug());
		fc.set("RadiusSendPackets", getRadius());
		fc.set("Delay-before-starting-staggered-runnable-ticks", getDelayStart());
		fc.set("Delay-between-restarting-staggered-runnable-ticks", getDelayRestart());
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

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public boolean getWorld(String name){
		return LightSource.getInstance().getConfig().getBoolean("Worlds." + name);
	}
	
	public void setWorld(String name, boolean flag){
		LightSource.getInstance().getConfig().set("Worlds." + name, flag);
		LightSource.getInstance().saveConfig();
	}

	/**
	 * @return the delaystart
	 */
	public int getDelayStart() {
		return delaystart;
	}

	/**
	 * @param delaystart the delaystart to set
	 */
	public void setDelayStart(int delaystart) {
		this.delaystart = delaystart;
	}

	/**
	 * @return the delayrestart
	 */
	public int getDelayRestart() {
		return delayrestart;
	}

	/**
	 * @param delayrestart the delayrestart to set
	 */
	public void setDelayRestart(int delayrestart) {
		this.delayrestart = delayrestart;
	}
}