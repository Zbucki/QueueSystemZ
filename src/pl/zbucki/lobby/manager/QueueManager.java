package pl.zbucki.lobby.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import pl.zbucki.lobby.data.QueueData;
import pl.zbucki.lobby.util.Logger;
import pl.zbucki.lobby.util.Util;

public class QueueManager {

	private final List<QueueData> queues = new ArrayList<>();

	public QueueManager(Plugin plugin) {
		init(plugin.getConfig());
	}

	private void init(FileConfiguration cfg) {
		for (String id : cfg.getConfigurationSection("QUEUES").getKeys(false)) {
			String permission = cfg.getString("QUEUES." + id + ".PERMISSION");
			long timer = Util.getTime(cfg.getString("QUEUES." + id + ".TIMER"));
			queues.add(new QueueData(timer, permission));
		}
		Logger.info("Zaladowano " + queues.size() + " kolej(ki/ek)!");
	}

	public void addToQueue(Player p) {
		QueueData data = queues.stream().filter(que -> p.hasPermission(que.getPermission())).findFirst().orElse(null);
		if (data == null) {
			Logger.severe("Gracz " + p.getName() + " (" + p.getUniqueId() + ") nie zostal dodany do zadnej kolejki!");
			return;
		}
		data.addToQueue(p);
	}

	public void removeFromQueue(Player p) {
		queues.stream().forEach(que -> que.getQueue().remove(p.getUniqueId()));
	}

	public int getPosition(Player p) {
		QueueData data = queues.stream().filter(que -> que.getQueue().contains(p.getUniqueId())).findFirst()
				.orElse(null);
		return (data == null ? -1 : data.getPosition(p));
	}

	public void clearQueues() {
		queues.forEach(que -> que.getQueue().clear());
	}

	public List<QueueData> getQueues() {
		return queues;
	}

}
