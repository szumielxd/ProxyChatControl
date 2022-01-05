package me.szumielxd.proxychatcontrol.common.objects;

import java.net.SocketAddress;
import java.util.Collection;

import org.jetbrains.annotations.NotNull;

public interface CommonServer {
	
	
	public @NotNull Collection<CommonPlayer> getPlayers();
	
	public @NotNull String getName();
	
	public @NotNull SocketAddress getAddress();
	
	public boolean isRestricted();
	
	public @NotNull String getPermission();
	

}
