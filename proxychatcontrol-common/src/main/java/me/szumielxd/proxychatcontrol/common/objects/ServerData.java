package me.szumielxd.proxychatcontrol.common.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;

public class ServerData {
	
	
	private final @NotNull String serverName;
	private final @NotNull Collection<CommonPlayer> players;
	
	
	public ServerData(@NotNull String serverName, @NotNull Collection<CommonPlayer> players) {
		this.serverName = Objects.requireNonNull(serverName, "serverName cannot be null");
		this.players = Objects.requireNonNull(Collections.unmodifiableList(new ArrayList<>(players)), "players cannot be null");
	}
	
	
	public @NotNull String getName() {
		return this.serverName;
	}
	
	
	public @NotNull Collection<CommonPlayer> getPlayers() {
		return this.players;
	}
	

}
