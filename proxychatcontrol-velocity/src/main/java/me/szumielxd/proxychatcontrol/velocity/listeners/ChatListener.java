package me.szumielxd.proxychatcontrol.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;
import com.velocitypowered.api.proxy.Player;

import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.utils.ChatUtil;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatListener {
	
	
	// MUTE CHAT
	@Subscribe(order = PostOrder.LATE)
	public void checkMutedChat(PlayerChatEvent event) {
		if (!event.getResult().isAllowed()) return;
		Player player = event.getPlayer();
		if (!player.hasPermission("bungeechatcontrol.bypass.chat") && !player.getCurrentServer().filter(s -> ChatUtil.isAllowed(s.getServerInfo().getName())).isPresent()) {
			event.setResult(ChatResult.denied());
			player.sendMessage(LegacyComponentSerializer.legacySection().deserialize(Config.LANGUAGE_CHAT_DENY_MESSAGE.toString()));
		}
	}

}
