package me.szumielxd.proxychatcontrol.common.commands.subcommands;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.szumielxd.proxychatcontrol.common.ProxyChatControlProvider;
import me.szumielxd.proxychatcontrol.common.commands.ArgumentType;
import me.szumielxd.proxychatcontrol.common.commands.SubCommand;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.data.Pair;
import me.szumielxd.proxychatcontrol.common.objects.CommonSender;
import me.szumielxd.proxychatcontrol.common.objects.CommonServer;
import me.szumielxd.proxychatcontrol.common.utils.ChatUtil;
import me.szumielxd.proxychatcontrol.common.utils.MiscUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

public class ListCommand extends SubCommand {

	public ListCommand(String name, String descr, String... aliases) {
		super(name, descr, aliases);
	}

	@Override
	public boolean execute(CommonSender s, List<Pair<ArgumentType, Object>> args) {
		Map<String, CommonServer> servers = ProxyChatControlProvider.get().getProxyServer().getServers();
		Component message = MiscUtil.parseComponent(Config.COMMANDS_LIST_START.getString(), false);
		boolean first = false;
		if(servers != null && !servers.isEmpty()) {
			for(String srv : servers.keySet()) {
				String status = ChatUtil.isAllowed(srv)? Config.COMMANDS_LIST_ENABLED.getString() : Config.COMMANDS_LIST_DISABLED.getString();
				Component name = MiscUtil.parseComponent(status+srv, false);
				CommonServer info = servers.get(srv);
				String online = info.getPlayers().size()+"";
				String restricted = MiscUtil.parseStatus(info.isRestricted());
				String stat = MiscUtil.parseBool(ChatUtil.isExactlyAllowed(srv));
				String perm = info.getPermission();
				String ip = "localhost";
				String port = "-1";
				if(info.getAddress() instanceof InetSocketAddress) {
					InetSocketAddress socket = (InetSocketAddress) info.getAddress();
					port = socket.getPort()+"";
					ip = socket.getAddress().getHostAddress();
				}
				if(!Config.COMMANDS_LIST_HOVER.getString().isEmpty()) {
					String hover = String.join("\n", Config.COMMANDS_LIST_HOVER.getString());
					hover = hover.replace("%server%", srv);
					hover = hover.replace("%online%", online);
					hover = hover.replace("%restricted%", restricted);
					hover = hover.replace("%status%", stat);
					hover = hover.replace("%permission%", perm);
					hover = hover.replace("%ip%", ip);
					hover = hover.replace("%port%", port);
					name = name.hoverEvent(MiscUtil.parseComponent(hover, false));
				}
				if(!Config.COMMANDS_LIST_CLICK.getString().isEmpty()) {
					String[] arr = Config.COMMANDS_LIST_CLICK.getString().split(": ", 2);
					ClickEvent.Action act = null;
					String cmd= "";
					if(arr.length == 2) {
						act = ClickEvent.Action.valueOf(arr[0].toUpperCase());
						cmd = arr[1];
					}
					if(act == null) {
						act = ClickEvent.Action.RUN_COMMAND;
						cmd = Config.COMMANDS_LIST_CLICK.getString();
					}
					cmd = cmd.replace("%server%", srv);
					cmd = cmd.replace("%online%", online);
					cmd = cmd.replace("%restricted%", restricted);
					cmd = cmd.replace("%status%", stat);
					cmd = cmd.replace("%permission%", perm);
					cmd = cmd.replace("%ip%", ip);
					cmd = cmd.replace("%port%", port);
					name = name.clickEvent(ClickEvent.clickEvent(act, cmd));
				}
				message = message.append(name);
				if(!first) first = true;
				else message = message.append(MiscUtil.parseComponent(Config.COMMANDS_LIST_SEPARATOR.getString(), false));
			}
		}
		s.sendMessage(message);
		return true;
	}

	@Override
	public List<Pair<ArgumentType, Boolean>> getArguments() {
		return new ArrayList<Pair<ArgumentType, Boolean>>();
	}

}
