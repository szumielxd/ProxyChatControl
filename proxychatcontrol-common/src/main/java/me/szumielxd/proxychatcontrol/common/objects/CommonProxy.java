package me.szumielxd.proxychatcontrol.common.objects;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CommonProxy {
	
	
	public @Nullable CommonPlayer getPlayer(@NotNull UUID uuid);
	
	public @Nullable CommonPlayer getPlayer(@NotNull String name);
	
	public @NotNull Collection<CommonPlayer> getPlayers();
	
	public @NotNull Optional<Collection<CommonPlayer>> getPlayers(@NotNull String serverName);
	
	public @NotNull Map<String, CommonServer> getServers();
	
	public @NotNull Optional<CommonServer> getServer(@NotNull String serverName);
	
	public @NotNull CommonSender getConsole();
	
	public @NotNull CommonScheduler getScheduler();
	

}
