package me.szumielxd.proxychatcontrol.velocity.objects;

import java.net.SocketAddress;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import me.szumielxd.proxychatcontrol.common.objects.CommonPlayer;
import me.szumielxd.proxychatcontrol.common.objects.CommonServer;
import me.szumielxd.proxychatcontrol.velocity.ProxyChatControlVelocity;

public class VelocityServer implements CommonServer {
	
	
	private final @NotNull ProxyChatControlVelocity plugin;
	private final @NotNull RegisteredServer server;
	
	
	public VelocityServer(@NotNull ProxyChatControlVelocity plugin, @NotNull RegisteredServer server) {
		this.plugin = Objects.requireNonNull(plugin, "plugin cannot be null");
		this.server = Objects.requireNonNull(server, "server cannot be null");
	}
	

	@Override
	public @NotNull Collection<CommonPlayer> getPlayers() {
		return this.server.getPlayersConnected().parallelStream().map(p -> new VelocityPlayer(this.plugin, p)).collect(Collectors.toList());
	}

	@Override
	public @NotNull String getName() {
		return this.server.getServerInfo().getName();
	}
	
	@Override
	public @NotNull SocketAddress getAddress() {
		return this.server.getServerInfo().getAddress();
	}
	
	@Override
	public boolean isRestricted() {
		return false;
	}
	
	@Override
	public @NotNull String getPermission() {
		return "";
	}

}
