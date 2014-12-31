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
         * Task delay: 15
         * max-iterations-per-tick: 5
         * ignore-save-update-light: false
         */
        SAVE,

        /**
         * player light : true
         * entity light: true
         * item light: true
         * Task delay: 5
         * max-iterations-per-tick: 15
         * ignore-save-update-light: true
         */
        MAXIMUM,

        /**
         * player light : true
         * entity light: false
         * item light: false
         * Task delay: 10
         * max-iterations-per-tick: 10
         * ignore-save-update-light: false
         */
        USER;
    }

    /**
     * Enum for Main task mode
     */
    public enum TaskMode {
        ONE_FOR_ALL, ONE_FOR_ONE;
    }

    private LightSource plugin;
    private boolean playerlight;
    private boolean entitylight;
    private boolean itemlight;
    private boolean lightSourceDamage;
    private boolean ignoreSaveUpdate;
    private boolean burnLight;

    // private boolean updater;
    private int taskticks;
    private int maxIterationsPerTick;
    private int damageFire;

    private UpdateMode strategyUpdate = UpdateMode.USER;
    private TaskMode task = TaskMode.ONE_FOR_ALL;

    public LSConfig(LightSource plugin) {
        this.plugin = plugin;

        if (plugin.getConfig().getString("LightUpdateMode") != null) {
            String mode = plugin.getConfig().getString("LightUpdateMode");
            if (UpdateMode.valueOf(mode) != null) {
                strategyUpdate = UpdateMode.valueOf(mode);
            }
        }

        if (plugin.getConfig().getString("TaskMode") != null) {
            String task = plugin.getConfig().getString("TaskMode");
            if (TaskMode.valueOf(task) != null) {
                this.task = TaskMode.valueOf(task);
            }
        }

        if (strategyUpdate == UpdateMode.SAVE) {
            setPlayerLight(true);
            setEntityLight(false);
            setItemLight(false);
            setBurnLight(false);
            setTaskTicks(15);
            setMaxIterationsPerTick(5);
            setIgnoreSaveUpdate(false);
        } else if (strategyUpdate == UpdateMode.MAXIMUM) {
            setPlayerLight(true);
            setEntityLight(true);
            setItemLight(true);
            setBurnLight(true);
            setTaskTicks(5);
            setMaxIterationsPerTick(15);
            setIgnoreSaveUpdate(true);
        } else {
            setPlayerLight(plugin.getConfig().getBoolean("PlayerLight"));
            setEntityLight(plugin.getConfig().getBoolean("EntityLight"));
            setItemLight(plugin.getConfig().getBoolean("ItemLight"));
            setBurnLight(plugin.getConfig().getBoolean("BurnLight"));
            // setUpdater(plugin.getConfig().getBoolean("Enable-updater"));
            setTaskTicks(plugin.getConfig().getInt("Task-delay-ticks"));
            setMaxIterationsPerTick(plugin.getConfig().getInt("max-iterations-per-tick"));
            setIgnoreSaveUpdate(plugin.getConfig().getBoolean("Ignore-save-update-light"));
        }

        setLightSourceDamage(plugin.getConfig().getBoolean("LightSourceDamage"));
        setDamageFire(plugin.getConfig().getInt("Damage-fire-ticks-sec"));
    }

    public void save() {
        if (strategyUpdate == UpdateMode.USER) {
            FileConfiguration fc = plugin.getConfig();
            fc.set("LightUpdateMode", strategyUpdate.name());
            fc.set("TaskMode", task.name());
            fc.set("PlayerLight", isPlayerLight());
            fc.set("EntityLight", isEntityLight());
            fc.set("ItemLight", isItemLight());
            fc.set("BurnLight", isBurnLight());
            fc.set("LightSourceDamage", isLightSourceDamage());
            fc.set("Ignore-save-update-light", isIgnoreSaveUpdate());
            // fc.set("Enable-updater", isUpdater());
            fc.set("Task-delay-ticks", getTaskTicks());
            fc.set("max-iterations-per-tick", getMaxIterationsPerTick());
            fc.set("Damage-fire-ticks-sec", getDamageFire());
            plugin.saveConfig();
        }
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

    /**
     * @return the damageFire
     */
    public int getDamageFire() {
        return damageFire;
    }

    /**
     * @param damageFire
     *            the damageFire to set
     */
    public void setDamageFire(int damageFire) {
        this.damageFire = damageFire;
    }

    /**
     * @return the lightSourceDamage
     */
    public boolean isLightSourceDamage() {
        return lightSourceDamage;
    }

    /**
     * @param lightSourceDamage
     *            the lightSourceDamage to set
     */
    public void setLightSourceDamage(boolean lightSourceDamage) {
        this.lightSourceDamage = lightSourceDamage;
    }

    /**
     * @return the ignoreSaveUpdate
     */
    public boolean isIgnoreSaveUpdate() {
        return ignoreSaveUpdate;
    }

    /**
     * @param ignoreSaveUpdate
     *            the ignoreSaveUpdate to set
     */
    public void setIgnoreSaveUpdate(boolean ignoreSaveUpdate) {
        this.ignoreSaveUpdate = ignoreSaveUpdate;
    }

    /**
     * @return the burnLight
     */
    public boolean isBurnLight() {
        return burnLight;
    }

    /**
     * @param burnLight
     *            the burnLight to set
     */
    public void setBurnLight(boolean burnLight) {
        this.burnLight = burnLight;
    }

    /**
     * @return the task
     */
    public TaskMode getTaskMode() {
        return task;
    }
}