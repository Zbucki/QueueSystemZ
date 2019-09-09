package pl.zbucki.lobby.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Settings {

	public final String PLAYER_QUEUE;
	public final String SERVER;
	public final String PLAYER_INFO_JOIN;

	public Settings(Plugin plugin) {
		FileConfiguration cfg = plugin.getConfig();
		PLAYER_QUEUE = cfg.getString("PLAYER_INFO.POSITION");
		PLAYER_INFO_JOIN = cfg.getString("PLAYER_INFO.JOIN");
		SERVER = cfg.getString("SERVER");
	}

}
