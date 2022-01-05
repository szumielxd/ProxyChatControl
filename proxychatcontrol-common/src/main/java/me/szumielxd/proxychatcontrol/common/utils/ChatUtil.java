package me.szumielxd.proxychatcontrol.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator.OfInt;

import me.szumielxd.proxychatcontrol.common.ProxyChatControlProvider;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.objects.CommonPlayer;
import net.kyori.adventure.text.Component;

import java.util.Random;

public class ChatUtil {
	
	private static List<String> disabled = new ArrayList<String>();
	
	
	// CHECK IF SERVER HAS ENABLED CHAT
	public static boolean isAllowed(String server) {
		if(disabled.contains("*")) return false;
		if(disabled.contains(server.toLowerCase())) return false;
		return true;
	}
	
	
	public static boolean isExactlyAllowed(String server) {
		if(disabled.contains(server.toLowerCase())) return false;
		return true;
	}
	
	
	// ENABLE CHAT ON SERVER. USE * FOR GLOBAL
	public static boolean allow(String server) {
		return disabled.remove(server.toLowerCase());
	}
	
	
	// DISABLE CHAT ON SERVER. USE * FOR GLOBAL
	public static boolean disallow(String server) {
		if(disabled.contains(server)) return false;
		return disabled.add(server.toLowerCase());
	}
	
	
	/*
	 * List of methods used to clear chat
	 * Default values:
	 * - server = "*"
	 * - ignorePermissions = false
	 */
	public static void chatClear() {
		clearChat("*", false);
	}
	public static void chatClear(String server) {
		clearChat(server, false);
	}
	public static void chatClear(boolean ignorePermissions) {
		clearChat("*", ignorePermissions);
	}
	public static void clearChat(String server, boolean ignorePermissions) {
		Optional<Collection<CommonPlayer>> players = ProxyChatControlProvider.get().getProxyServer().getPlayers(server);
		if (!players.isPresent()) return;
		Collection<CommonPlayer> list = players.get();
		int lines = 300;
		String[] texts = new String[]{"   ", "          ", "      ", "               ", " ", "                     "};
		OfInt ints = new Random().ints(lines, 0, Integer.MAX_VALUE).iterator();
		Component[] clear = new Component[lines];
		for(int i=0; i<lines; i++) {
			clear[i] = Component.text(texts[ints.next()%texts.length]);
		}
		if(list == null || list.isEmpty()) return;
		if(ignorePermissions) {
			for(CommonPlayer p : list) {
				for(int i=0; i<lines; i++) {
					p.sendMessage(clear[i]);
				}
			}
		} else {
			for(CommonPlayer p : list) {
				if(!p.hasPermission("bungeechatcontrol.bypass.clear")) for(int i=0; i<lines; i++) {
					p.sendMessage(clear[i]);
				}
			}
		}
	}
	
	
	public static void sendClearMessage(String server, String cleaner) {
		Optional<Collection<CommonPlayer>> players = ProxyChatControlProvider.get().getProxyServer().getPlayers(server);
		if (!players.isPresent()) return;
		String msg = (cleaner == null) ? Config.LANGUAGE_CHAT_CLEAR_INFO_INCOGNITO.getString() : Config.LANGUAGE_CHAT_CLEAR_INFO.getString().replace("%sender%", cleaner);
		if(msg == null || msg.isEmpty()) return;
		Component text = MiscUtil.parseComponent(msg, false);
		ProxyChatControlProvider.get().getProxyServer().getConsole().sendMessage(text);
		players.get().forEach(p -> p.sendMessage(text));
	}
	

}
