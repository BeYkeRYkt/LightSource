package ykt.BeYkeRYkt.LightSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	private FileConfiguration customConfig;
	private File customFile;
	private String name;

	public Config(String name) {
		this.name = name;
	}

	public void reloadSourceConfig() {
		if (this.customFile == null) {
			String folder = LightSource.getInstance().getDataFolder() + "";
			this.customFile = new File(folder, name + ".yml");
		}
		this.customConfig = YamlConfiguration
				.loadConfiguration(this.customFile);

		// Look for defaults in the jar
		InputStream defConfigStream = LightSource.getInstance().getResource(
				name + ".yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			this.customConfig.setDefaults(defConfig);
		}
	}

	public FileConfiguration getSourceConfig() {
		if (this.customConfig == null) {
			reloadSourceConfig();
		}
		return this.customConfig;
	}

	public void saveSourceConfig() {
		if (this.customConfig == null || this.customFile == null) {
			return;
		}
		try {
			getSourceConfig().save(this.customFile);
		} catch (IOException ex) {
			LightSource
					.getInstance()
					.getLogger()
					.log(Level.SEVERE,
							"Could not save config to " + this.customFile, ex);
		}
	}

}