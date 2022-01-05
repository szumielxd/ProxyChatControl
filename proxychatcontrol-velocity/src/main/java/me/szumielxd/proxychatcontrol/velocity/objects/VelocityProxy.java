package me.szumielxd.proxychatcontrol.velocity.objects;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.velocitypowered.api.proxy.server.RegisteredServer;

import me.szumielxd.proxychatcontrol.common.objects.CommonPlayer;
import me.szumielxd.proxychatcontrol.common.objects.CommonProxy;
import me.szumielxd.proxychatcontrol.common.objects.CommonScheduler;
import me.szumielxd.proxychatcontrol.common.objects.CommonSender;
import me.szumielxd.proxychatcontrol.common.objects.CommonServer;
import me.szumielxd.proxychatcontrol.velocity.ProxyChatControlVelocity;

public class VelocityProxy implements CommonProxy {
	
	
	private final @NotNull ProxyChatControlVelocity plugin;
	private final @NotNull VelocityScheduler scheduler;
	
	
	public VelocityProxy(@NotNull ProxyChatControlVelocity plugin) {
		this.plugin = plugin;
		this.scheduler = new VelocityScheduler(plugin);
	}
	

	@Override
	public @Nullable CommonPlayer getPlayer(@NotNull UUID uuid) {
		return this.plugin.getProxy().getPlayer(uuid).map(p -> new VelocityPlayer(this.plugin, p)).orElse(null);
	}

	@Override
	public @Nullable CommonPlayer getPlayer(@NotNull String name) {
		return this.plugin.getProxy().getPlayer(name).map(p -> new VelocityPlayer(this.plugin, p)).orElse(null);
	}

	@Override
	public @NotNull Collection<CommonPlayer> getPlayers() {
		return this.plugin.getProxy().getAllPlayers().parallelStream().map(p -> new VelocityPlayer(this.plugin, p)).collect(Collectors.toList());
	}

	@Override
	public @NotNull Optional<Collection<CommonPlayer>> getPlayers(@NotNull String serverName) {
		return this.plugin.getProxy().getServer(Objects.requireNonNull(serverName, "serverName cannot be null"))
				.map(RegisteredServer::getPlayersConnected)
				.map(list -> list.parallelStream().map(p -> new VelocityPlayer(this.plugin, p)).collect(Collectors.toList()));
	}
	
	@Override
	public @NotNull Map<String, CommonServer> getServers() {
		return this.plugin.getProxy().getAllServers().parallelStream().collect(Collectors.toMap(s -> s.getServerInfo().getName(), s -> new VelocityServer(this.plugin, s)));
	}
	
	@Override
	public @NotNull Optional<CommonServer> getServer(@NotNull String serverName) {
		return this.plugin.getProxy().getServer(serverName).map(s -> new VelocityServer(this.plugin, s));
	}
	
	@Override
	public @NotNull CommonSender getConsole() {
		return VelocitySender.wrap(this.plugin, this.plugin.getProxy().getConsoleCommandSource());
	}
	
	@Override
	public @NotNull CommonScheduler getScheduler() {
		return this.scheduler;
	}
	

}
