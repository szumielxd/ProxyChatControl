package me.szumielxd.proxychatcontrol.bungee.objects;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import me.szumielxd.proxychatcontrol.bungee.ProxyChatControlBungee;
import me.szumielxd.proxychatcontrol.common.objects.CommonPlayer;
import me.szumielxd.proxychatcontrol.common.objects.CommonServer;
import net.md_5.bungee.api.config.ServerInfo;

public class BungeeServer implements CommonServer {
	
	
	private final @NotNull ProxyChatControlBungee plugin;
	private final @NotNull ServerInfo server;
	
	
	public BungeeServer(@NotNull ProxyChatControlBungee plugin, @NotNull ServerInfo server) {
		this.plugin = Objects.requireNonNull(plugin, "plugin cannot be null");
		this.server = Objects.requireNonNull(server, "server cannot be null");
	}
	

	@Override
	public @NotNull Collection<CommonPlayer> getPlayers() {
		return this.server.getPlayers().parallelStream().map(p -> new BungeePlayer(this.plugin, p)).collect(Collectors.toList());
	}

	@Override
	public @NotNull String getName() {
		return this.server.getName();
	}
	
	@Override
	public @NotNull SocketAddress getAddress() {
		return this.server.getSocketAddress();
	}
	
	@Override
	public boolean isRestricted() {
		return this.server.isRestricted();
	}
	
	@Override
	public @NotNull String getPermission() {
		return this.server.getPermission();
	}

}
