package me.szumielxd.proxychatcontrol.common.commands.subcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.szumielxd.proxychatcontrol.common.ProxyChatControlProvider;
import me.szumielxd.proxychatcontrol.common.commands.ArgumentType;
import me.szumielxd.proxychatcontrol.common.commands.SubCommand;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.data.Pair;
import me.szumielxd.proxychatcontrol.common.objects.CommonPlayer;
import me.szumielxd.proxychatcontrol.common.objects.CommonSender;
import me.szumielxd.proxychatcontrol.common.objects.ServerData;
import me.szumielxd.proxychatcontrol.common.utils.ChatUtil;
import me.szumielxd.proxychatcontrol.common.utils.MiscUtil;
import net.kyori.adventure.text.Component;

public class ToggleChatCommand extends SubCommand {

	public ToggleChatCommand(String name, String descr, String... aliases) {
		super(name, descr, aliases);
		this.staticArgs = new String[] {"-incognito -inc -i"};
	}

	@Override
	public boolean execute(CommonSender s, List<Pair<ArgumentType, Object>> args) {
		String server;
		if(s instanceof CommonPlayer) server = ((CommonPlayer)s).getWorldName();
		else server  = "*";
		boolean incognito = false;
		Object arg1 = args.get(0).getRight();
		Object arg2 = args.get(1).getRight();
		Object arg3 = args.get(2).getRight();
		if(arg2 != null && arg2 instanceof ServerData) server = ((ServerData) arg2).getName();
		if(arg3 != null) incognito = true;
		boolean on = !ChatUtil.isExactlyAllowed(server);
		if(arg1 != null && arg1 instanceof Boolean) {
			boolean bool = (Boolean)arg1;
			if(bool != on) {
				s.sendMessage(MiscUtil.parseComponent(Config.PREFIX.getString()+(bool? Config.COMMANDS_TOGGLE_ALREADY_ON : Config.COMMANDS_TOGGLE_ALREADY_OFF).getString(), false));
				return true;
			}
		}
		if(on) ChatUtil.allow(server);
		else ChatUtil.disallow(server);
		Optional<List<CommonPlayer>> players = ProxyChatControlProvider.get().getProxyServer().getPlayers(server).map(ArrayList::new);
		Component text = on ? MiscUtil.parseComponent(incognito? Config.COMMANDS_TOGGLE_INFO_ON_INCOGNITO.getString() : Config.COMMANDS_TOGGLE_INFO_ON.getString().replace("%sender%", s.getName()), false)
				: MiscUtil.parseComponent(incognito? Config.COMMANDS_TOGGLE_INFO_OFF_INCOGNITO.getString() : Config.COMMANDS_TOGGLE_INFO_OFF.getString().replace("%sender%", s.getName()), false);
		if(players.isPresent()) players.get().forEach(p -> p.sendMessage(text));
		
		if(server.equals("*")) s.sendMessage(MiscUtil.parseComponent(Config.PREFIX.getString()+(on? Config.COMMANDS_TOGGLE_DONE_ON_GLOBAL : Config.COMMANDS_TOGGLE_DONE_OFF_GLOBAL).getString(), false));
		else s.sendMessage(MiscUtil.parseComponent(Config.PREFIX.getString()+(on? Config.COMMANDS_TOGGLE_DONE_ON : Config.COMMANDS_TOGGLE_DONE_OFF).getString().replace("%server%", server), false));
		
		return true;
	}
	
	@Override
	public List<Pair<ArgumentType, Boolean>> getArguments() {
		List<Pair<ArgumentType, Boolean>> list = new ArrayList<Pair<ArgumentType, Boolean>>();
		list.add(new Pair<>(ArgumentType.BOOLEAN, false)); //ON-OFF
		list.add(new Pair<>(ArgumentType.SERVER, false)); //SERVER
		list.add(new Pair<>(ArgumentType.STATIC, false)); //-incognito -inc -i
		return list;
	}

}
