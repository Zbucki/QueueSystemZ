package pl.zbucki.lobby.util;

import java.util.Stack;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import pl.zbucki.lobby.Main;

public final class Util {

	public static String fixColor(final String s) {
		if (s == null) {
			return "";
		}
		String m = ChatColor.translateAlternateColorCodes('&', s);
		return m.replace("%>", "»").replace("<%", "«");
	}

	public static boolean sendMsg(final CommandSender sender,
			final String message) {
		if (sender instanceof Player) {
			if (message != null || message != "") {
				sender.sendMessage(fixColor(message));
			}
		} else {
			sender.sendMessage(ChatColor.stripColor(fixColor(message)));
		}
		return true;
	}
	
	public static long getTime(final String string) {
		if (string == null || string.isEmpty()) {
			return 0L;
		}
		final Stack<Character> type = new Stack<Character>();
		final StringBuilder value = new StringBuilder();
		boolean calc = false;
		long time = 0L;
		char[] chars;
		for (int l = (chars = string.toCharArray()).length, j = 0; j < l; ++j) {
			final char c = chars[j];
			switch (c) {
			case 'd':
			case 'h':
			case 'm':
			case 's': {
				if (!calc) {
					type.push(c);
					calc = true;
				}
				if (calc) {
					try {
						final long i = Integer.valueOf(value.toString());
						switch ((char) type.pop()) {
						case 'd': {
							time += i * (20*60*60*24);
							break;
						}
						case 'h': {
							time += i * (20*60*60);
							break;
						}
						case 'm': {
							time += i * (20*60L);
							break;
						}
						case 's': {
							time += i * 20L;
							break;
						}
						}
					} catch (NumberFormatException e) {
						return time;
					}
				}
				type.push(c);
				calc = true;
				break;
			}
			default: {
				value.append(c);
				break;
			}
			}
		}
		return time;
	}
	
	public static void teleportToServer(Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(Main.getSettings().SERVER);
        p.sendPluginMessage(Main.getInst(), "BungeeCord", out.toByteArray());
	}
}
