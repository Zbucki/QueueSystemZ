package pl.zbucki.lobby.util.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import pl.zbucki.lobby.Main;
import pl.zbucki.lobby.data.QueueData;

public class ActionBarRunnable implements Runnable {
	
	public ActionBarRunnable(Main plugin) {
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, 15, 15);
	}

	public void run() {
		List<QueueData> queues = new ArrayList<>(Main.getManager().getQueues());
		if (queues.isEmpty()) {
			return;
		}
		queues.forEach(queue -> queue.applyActionBar());
	}

}
