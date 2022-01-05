package me.szumielxd.proxychatcontrol.common.commands;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.szumielxd.proxychatcontrol.common.ProxyChatControlProvider;
import me.szumielxd.proxychatcontrol.common.objects.CommonProxy;
import me.szumielxd.proxychatcontrol.common.objects.ServerData;

public enum ArgumentType {
	
	
	TEXT(String.class, Function.identity()),
	NUMBER(Double.class, (arg) -> {
		try {
			return Double.parseDouble(arg);
		} catch (NumberFormatException e) {
			return null;
		}
	}),
	SERVER(ServerData.class, (arg) -> {
		CommonProxy srv = ProxyChatControlProvider.get().getProxyServer();
		if ("*".equals(arg)) return new ServerData("*", srv.getPlayers());
		return srv.getPlayers(arg).flatMap(players -> srv.getServer(arg).map(s -> new ServerData(s.getName(), players))).orElse(null);
	}),
	BOOLEAN(Boolean.class, (arg) -> {
		if(arg == null) return null;
		if(Arrays.asList(new String[] {"yes", "y", "true", "t", "on", "allow"}).contains(arg.toLowerCase())) return true;
		if(Arrays.asList(new String[] {"no", "n", "false", "f", "off", "deny"}).contains(arg.toLowerCase())) return false;
		return null;
	}),
	INTEGER(Integer.class, (arg) -> {
		try {
			return Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			return null;
		}
	}),
	STATIC(String.class, Function.identity());
	
	
	private final @NotNull Class<?> type;
	private final @NotNull Function<String, ?> parser;
	private @NotNull String display;
	
	ArgumentType(@NotNull Class<?> type, @NotNull Function<String,?> parser) {
		this.type = Objects.requireNonNull(type, "type cannot be null");
		this.parser = Objects.requireNonNull(parser, "parser cannot be null");
	}
	
	public @NotNull Class<?> getType(){
		return this.type;
	}
	
	public @NotNull String getDisplay() {
		return new String(this.display);
	}
	
	public void setDisplay(@NotNull String name) {
		this.display = Objects.requireNonNull(name, "name cannot be null");
	}
	
	public @Nullable Object parseArg(@NotNull String argument) {
		return this.parser.apply(Objects.requireNonNull(argument, "argument cannot be null"));
	}
	

}
