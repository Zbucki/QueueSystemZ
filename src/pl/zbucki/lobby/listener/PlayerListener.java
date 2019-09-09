package pl.zbucki.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import pl.zbucki.lobby.Main;

public class PlayerListener implements Listener{
	
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e){
		Main.getManager().addToQueue(e.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e){
		Main.getManager().removeFromQueue(e.getPlayer());
	}

}
