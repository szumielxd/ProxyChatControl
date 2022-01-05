package me.szumielxd.proxychatcontrol.bungee;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

import me.szumielxd.proxychatcontrol.bungee.commands.BungeeCommandWrapper;
import me.szumielxd.proxychatcontrol.bungee.listeners.ChatListener;
import me.szumielxd.proxychatcontrol.bungee.objects.BungeeProxy;
import me.szumielxd.proxychatcontrol.common.ProxyChatControl;
import me.szumielxd.proxychatcontrol.common.ProxyChatControlProvider;
import me.szumielxd.proxychatcontrol.common.commands.CommonCommand;
import me.szumielxd.proxychatcontrol.common.commands.MainCommand;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.objects.CommonProxy;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

public class ProxyChatControlBungee extends Plugin implements ProxyChatControl {
	
	
	private BungeeAudiences adventure = null;
	private @NotNull BungeeProxy proxy;
	
	
	public BungeeAudiences adventure() {
		if (this.adventure == null) throw new IllegalStateException("Cannot retrieve audience provider while plugin is not enabled");
		return this.adventure;
	}
	
	
	@Override
	public void onEnable() {
		ProxyChatControlProvider.init(this);
		this.proxy = new BungeeProxy(this);
		this.adventure = BungeeAudiences.create(this);
		Config.load(new File(this.getDataFolder(), "config.yml"), this);
		this.registerCommand(new MainCommand(this));
		this.getProxy().getPluginManager().registerListener(this, new ChatListener());
	}
	
	
	private void registerCommand(@NotNull CommonCommand command) {
		this.getProxy().getPluginManager().registerCommand(this, new BungeeCommandWrapper(this, command));
	}
	
	
	@Override
	public void onDisable() {
		this.getLogger().info("Disabling all announcements...");
		this.getProxy().getScheduler().cancel(this);
		if (this.adventure != null) {
			this.adventure.close();
			this.adventure = null;
		}
		try {
			Class<?> BungeeAudiencesImpl = Class.forName("net.kyori.adventure.platform.bungeecord.BungeeAudiencesImpl");
			Field f = BungeeAudiencesImpl.getDeclaredField("INSTANCES");
			f.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, BungeeAudiences> INSTANCES = (Map<String, BungeeAudiences>) f.get(null);
			INSTANCES.remove(this.getDescription().getName());
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		this.getProxy().getPluginManager().unregisterCommands(this);
		this.getLogger().info("Well done. Time to sleep!");
	}


	@Override
	public @NotNull CommonProxy getProxyServer() {
		return this.proxy;
	}


	@Override
	public @NotNull String getName() {
		return this.getDescription().getName();
	}


	@Override
	public @NotNull String getVersion() {
		return this.getDescription().getVersion();
	}
	

}
