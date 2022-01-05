package me.szumielxd.proxychatcontrol.bungee.listeners;

import me.szumielxd.proxychatcontrol.common.ProxyChatControlProvider;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.utils.ChatUtil;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ChatListener implements Listener {
	
	
	// MUTE CHAT
	@EventHandler(priority = EventPriority.HIGH)
	public void checkMutedChat(ChatEvent event) {
		if(event.isCancelled()) return;
		if (!event.isCommand()) {
			if(event.getSender() instanceof ProxiedPlayer) {
				ProxiedPlayer player = (ProxiedPlayer) event.getSender();
				if(!player.hasPermission("bungeechatcontrol.bypass.chat")) {
					if(!ChatUtil.isAllowed(player.getServer().getInfo().getName())) {
						event.setCancelled(true);
						ProxyChatControlProvider.get().getProxyServer().getPlayer(player.getUniqueId()).sendMessage(LegacyComponentSerializer.legacySection().deserialize(Config.LANGUAGE_CHAT_DENY_MESSAGE.getString()));
						player.sendMessage(new TextComponent());
					}
				}
			}
		}	
	}
	

}
