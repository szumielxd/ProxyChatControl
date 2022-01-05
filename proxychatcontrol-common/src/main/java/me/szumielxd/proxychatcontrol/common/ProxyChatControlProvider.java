package me.szumielxd.proxychatcontrol.common;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProxyChatControlProvider {
	
	
	private static @Nullable ProxyChatControl instance = null;
	
	
	public static void init(@NotNull ProxyChatControl instance) {
		ProxyChatControlProvider.instance = Objects.requireNonNull(instance, "instance cannot be null");
	}
	
	
	public static @NotNull ProxyChatControl get() {
		if (instance == null) throw new IllegalArgumentException("ProxyChatControl is not initialized");
		return instance;
	}
	

}
