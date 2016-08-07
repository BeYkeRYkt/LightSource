package ru.beykerykt.lightsource;

import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

	private double search_radius = 0;
	private int search_delay_ticks = 0;

	private boolean search_players;
	private boolean search_entities;
	private boolean search_items;

	private int update_ticks = 0;

	private boolean updater_enable;

	public void exportFromConfiguration(FileConfiguration config) {
		setUpdaterEnable(config.getBoolean("enable-updater"));
		setSearchPlayers(config.getBoolean("sources.search.search-players"));
		setSearchEntities(config.getBoolean("sources.search.search-entities"));
		setSearchItems(config.getBoolean("sources.search.search-items"));
		setSearchRadius(config.getDouble("sources.search.search-radius"));
		setSearchDelayTicks(config.getInt("sources.search.search-delay-ticks"));
		setUpdateTicks(config.getInt("sources.update-ticks"));
	}

	public double getSearchRadius() {
		return search_radius;
	}

	public void setSearchRadius(double search_radius) {
		this.search_radius = search_radius;
	}

	public int getSearchDelayTicks() {
		return search_delay_ticks;
	}

	public void setSearchDelayTicks(int search_delay_ticks) {
		this.search_delay_ticks = search_delay_ticks;
	}

	public int getUpdateTicks() {
		return update_ticks;
	}

	public void setUpdateTicks(int update_ticks) {
		this.update_ticks = update_ticks;
	}

	public boolean isSearchPlayers() {
		return search_players;
	}

	public void setSearchPlayers(boolean search_players) {
		this.search_players = search_players;
	}

	public boolean isSearchEntities() {
		return search_entities;
	}

	public void setSearchEntities(boolean search_entities) {
		this.search_entities = search_entities;
	}

	public boolean isSearchItems() {
		return search_items;
	}

	public void setSearchItems(boolean search_items) {
		this.search_items = search_items;
	}

	public boolean isUpdaterEnable() {
		return updater_enable;
	}

	public void setUpdaterEnable(boolean updater_enable) {
		this.updater_enable = updater_enable;
	}

}
