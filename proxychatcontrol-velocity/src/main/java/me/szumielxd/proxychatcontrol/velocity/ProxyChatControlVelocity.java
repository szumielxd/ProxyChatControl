package me.szumielxd.proxychatcontrol.velocity;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jetbrains.annotations.NotNull;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import me.szumielxd.proxychatcontrol.common.ProxyChatControl;
import me.szumielxd.proxychatcontrol.common.ProxyChatControlProvider;
import me.szumielxd.proxychatcontrol.common.commands.CommonCommand;
import me.szumielxd.proxychatcontrol.common.commands.MainCommand;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.objects.CommonProxy;
import me.szumielxd.proxychatcontrol.velocity.commands.VelocityCommandWrapper;
import me.szumielxd.proxychatcontrol.velocity.listeners.ChatListener;
import me.szumielxd.proxychatcontrol.velocity.objects.VelocityProxy;

@Plugin(
		id = "id----",
		name = "@pluginName@",
		version = "@version@",
		authors = { "@author@" },
		description = "@description@",
		url = "https://github.com/szumielxd/ProxyChatControl/"
)
public class ProxyChatControlVelocity implements ProxyChatControl {
	
	
	private final ProxyServer server;
	private final Logger logger;
	private final File dataFolder;
	
	
	private @NotNull VelocityProxy proxy;
	
	
	@Inject
	public ProxyChatControlVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
		this.server = server;
		this.logger = logger;
		this.dataFolder = dataDirectory.toFile();
	}
	
	
	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
	    this.onEnable();
	}
	
	
	@Override
	public void onEnable() {
		ProxyChatControlProvider.init(this);
		this.proxy = new VelocityProxy(this);
		Config.load(new File(this.dataFolder, "config.yml"), this);
		this.registerCommand(new MainCommand(this));
		this.getProxy().getEventManager().register(this, new ChatListener());
	}
	
	
	private void registerCommand(@NotNull CommonCommand command) {
		CommandManager mgr = this.getProxy().getCommandManager();
		CommandMeta meta = mgr.metaBuilder(command.getName()).aliases(command.getAliases()).build();
		mgr.register(meta, new VelocityCommandWrapper(this, command));
	}
	
	
	@Override
	public void onDisable() {
		this.getLogger().info("Disabling all announcements...");
		this.getProxyServer().getScheduler().cancelAll();
		this.getLogger().info("Well done. Time to sleep!");
	}
	
	
	@Override
	public @NotNull Logger getLogger() {
		return this.logger;
	}
	
	
	public @NotNull ProxyServer getProxy() {
		return this.server;
	}


	@Override
	public @NotNull CommonProxy getProxyServer() {
		return this.proxy;
	}


	@Override
	public @NotNull String getName() {
		return this.getProxy().getPluginManager().ensurePluginContainer(this).getDescription().getName().orElse("");
	}


	@Override
	public @NotNull String getVersion() {
		return this.getProxy().getPluginManager().ensurePluginContainer(this).getDescription().getVersion().orElse("");
	}
	

}
