package me.szumielxd.proxychatcontrol.bungee.objects;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.szumielxd.proxychatcontrol.bungee.ProxyChatControlBungee;
import me.szumielxd.proxychatcontrol.common.objects.CommonPlayer;
import me.szumielxd.proxychatcontrol.common.objects.CommonProxy;
import me.szumielxd.proxychatcontrol.common.objects.CommonScheduler;
import me.szumielxd.proxychatcontrol.common.objects.CommonSender;
import me.szumielxd.proxychatcontrol.common.objects.CommonServer;
import net.md_5.bungee.api.config.ServerInfo;

public class BungeeProxy implements CommonProxy {


	private final @NotNull ProxyChatControlBungee plugin;
	private final @NotNull BungeeScheduler scheduler;
	
	
	public BungeeProxy(@NotNull ProxyChatControlBungee plugin) {
		this.plugin = plugin;
		this.scheduler = new BungeeScheduler(plugin);
	}
	

	@Override
	public @Nullable CommonPlayer getPlayer(@NotNull UUID uuid) {
		return Optional.ofNullable(this.plugin.getProxy().getPlayer(uuid)).map(p -> new BungeePlayer(this.plugin, p)).orElse(null);
	}

	@Override
	public @Nullable CommonPlayer getPlayer(@NotNull String name) {
		return Optional.ofNullable(this.plugin.getProxy().getPlayer(name)).map(p -> new BungeePlayer(this.plugin, p)).orElse(null);
	}

	@Override
	public @NotNull Collection<CommonPlayer> getPlayers() {
		return this.plugin.getProxy().getPlayers().parallelStream().map(p -> new BungeePlayer(this.plugin, p)).collect(Collectors.toList());
	}

	@Override
	public @NotNull Optional<Collection<CommonPlayer>> getPlayers(@NotNull String serverName) {
		return Optional.ofNullable(this.plugin.getProxy().getServerInfo(Objects.requireNonNull(serverName, "serverName cannot be null")))
				.map(ServerInfo::getPlayers).map(players -> players.parallelStream().map(p -> new BungeePlayer(this.plugin, p)).collect(Collectors.toList()));
	}
	
	@Override
	public @NotNull Map<String, CommonServer> getServers() {
		return this.plugin.getProxy().getServers().values().parallelStream().collect(Collectors.toMap(s -> s.getName(), s -> new BungeeServer(this.plugin, s)));
	}
	
	@Override
	public @NotNull Optional<CommonServer> getServer(@NotNull String serverName) {
		return Optional.ofNullable(this.plugin.getProxy().getServerInfo(serverName)).map(s -> new BungeeServer(this.plugin, s));
	}
	
	@Override
	public @NotNull CommonSender getConsole() {
		return BungeeSender.wrap(this.plugin, this.plugin.getProxy().getConsole());
	}
	
	@Override
	public @NotNull CommonScheduler getScheduler() {
		return this.scheduler;
	}
	

}
