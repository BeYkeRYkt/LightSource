/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 - 2016
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *  
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package ru.beykerykt.lightsource;

import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

	private double search_radius = 0;
	private int search_delay_ticks = 0;

	private boolean search_players;
	private boolean search_entities;
	private boolean search_items;

	private int update_delay_ticks = 0;

	private boolean updater_enable;
	private String repo = "BeYkeRYkt/LightSource";
	private int updater_delay_ticks = 40;
	private boolean viewChangelog;

	private boolean lighting_queue;

	public void exportFromConfiguration(FileConfiguration config) {
		setAddToLightingQueue(config.getBoolean("add-to-async-lighting-queue"));
		setUpdaterEnable(config.getBoolean("updater.enable"));
		setRepo(config.getString("updater.repo"));
		setUpdaterDelayTicks(config.getInt("updater.update-delay-ticks"));
		setViewChangelog(config.getBoolean("updater.view-changelog"));
		setSearchPlayers(config.getBoolean("sources.search.search-players"));
		setSearchEntities(config.getBoolean("sources.search.search-entities"));
		setSearchItems(config.getBoolean("sources.search.search-items"));
		setSearchRadius(config.getDouble("sources.search.search-radius"));
		setSearchDelayTicks(config.getInt("sources.search.search-delay-ticks"));
		setUpdateDelayTicks(config.getInt("sources.update-delay-ticks"));
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

	public int getUpdateDelayTicks() {
		return update_delay_ticks;
	}

	public void setUpdateDelayTicks(int update_ticks) {
		this.update_delay_ticks = update_ticks;
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

	public boolean isAddToLightingQueue() {
		return lighting_queue;
	}

	public void setAddToLightingQueue(boolean lighting_queue) {
		this.lighting_queue = lighting_queue;
	}

	public boolean isUpdaterEnable() {
		return updater_enable;
	}

	public void setUpdaterEnable(boolean updater_enable) {
		this.updater_enable = updater_enable;
	}

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public int getUpdaterDelayTicks() {
		return updater_delay_ticks;
	}

	public void setUpdaterDelayTicks(int updater_delay_ticks) {
		this.updater_delay_ticks = updater_delay_ticks;
	}

	public boolean isViewChangelog() {
		return viewChangelog;
	}

	public void setViewChangelog(boolean viewChangelog) {
		this.viewChangelog = viewChangelog;
	}
}
