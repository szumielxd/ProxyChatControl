package me.szumielxd.proxychatcontrol.common;

import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import me.szumielxd.proxychatcontrol.common.objects.CommonProxy;

public interface ProxyChatControl {
	
	
	public @NotNull Logger getLogger();
	
	public @NotNull CommonProxy getProxyServer();
	
	public @NotNull String getName();
	
	public @NotNull String getVersion();
	
	
	public void onEnable();
	
	public void onDisable();
	

}
