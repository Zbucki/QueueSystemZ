package pl.zbucki.lobby;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.zbucki.lobby.config.Settings;
import pl.zbucki.lobby.listener.PlayerListener;
import pl.zbucki.lobby.manager.QueueManager;
import pl.zbucki.lobby.util.ActionBarAPI;
import pl.zbucki.lobby.util.runnable.ActionBarRunnable;

public class Main extends JavaPlugin {

	public static Main inst;
	public static Settings settings;
	public static QueueManager manager;
	public static ActionBarAPI actionbarapi;

	public void onEnable() {
		inst = this;
		saveDefaultConfig();
		settings = new Settings(this);
		manager = new QueueManager(this);
		actionbarapi = new ActionBarAPI(this);
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getOnlinePlayers().forEach(k -> manager.addToQueue(k));
		new ActionBarRunnable(this);
	}

	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		manager.clearQueues();
	}

	public static QueueManager getManager() {
		return manager;
	}

	public static Settings getSettings() {
		return settings;
	}

	public static ActionBarAPI getActionBarAPI() {
		return actionbarapi;
	}

	public static Main getInst() {
		return inst;
	}

}
