package pl.zbucki.lobby.data;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pl.zbucki.lobby.Main;
import pl.zbucki.lobby.util.Util;

public class QueueData {

	private Queue<UUID> queue;
	private final long timer;
	private final String permission;

	public QueueData(long timer, String permission) {
		this.queue = new LinkedList<>();
		this.timer = timer;
		this.permission = permission;
		start();
	}

	public Queue<UUID> getQueue() {
		return queue;
	}

	public String getPermission() {
		return permission;
	}

	public void addToQueue(Player p) {
		if (queue.contains(p.getUniqueId())) {
			return;
		}
		queue.offer(p.getUniqueId());
	}
	
	public int getPosition(Player p) {
		int i = 1;
		for (UUID uuid : queue) {
			Player o = Bukkit.getPlayer(uuid);
			if (o.getUniqueId().equals(p.getUniqueId())) {
				return i;
			}
			i++;
		}
		return 0;
	}

	public void applyActionBar() {
		queue.forEach(uuid -> {
			Player p = Bukkit.getPlayer(uuid);
			if (p.isOnline()) {
				Main.getActionBarAPI().sendActionBar(p, Util.fixColor(StringUtils.replace(Main.getSettings().PLAYER_QUEUE,
						"%pos%", String.valueOf(getPosition(p)))));
				return;
			}
		});
		
	}

	private void start() {
		Bukkit.getScheduler().runTaskTimer(Main.getInst(), new Runnable() {
			public void run() {
				if (queue.isEmpty()) {
					return;
				}
				Player p = Bukkit.getPlayer(queue.poll());
				if (p != null) {
					Main.getActionBarAPI().sendActionBar(p, " ");
					Util.teleportToServer(p);
					Util.sendMsg(p, Main.getSettings().PLAYER_INFO_JOIN);
				}
			}
		}, 1L, timer);
	}

}
