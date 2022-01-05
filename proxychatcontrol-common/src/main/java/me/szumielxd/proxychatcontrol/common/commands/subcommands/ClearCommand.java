package me.szumielxd.proxychatcontrol.common.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import me.szumielxd.proxychatcontrol.common.commands.ArgumentType;
import me.szumielxd.proxychatcontrol.common.commands.SubCommand;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.data.Pair;
import me.szumielxd.proxychatcontrol.common.objects.CommonPlayer;
import me.szumielxd.proxychatcontrol.common.objects.CommonSender;
import me.szumielxd.proxychatcontrol.common.objects.ServerData;
import me.szumielxd.proxychatcontrol.common.utils.ChatUtil;
import me.szumielxd.proxychatcontrol.common.utils.MiscUtil;

public class ClearCommand extends SubCommand {

	public ClearCommand(String name, String descr, String... aliases) {
		super(name, descr, aliases);
		this.staticArgs = new String[] {"-incognito -inc -i", "-perm -ignore"};
	}

	@Override
	public boolean execute(CommonSender s, List<Pair<ArgumentType, Object>> args) {
		String server;
		if(s instanceof CommonPlayer) server = ((CommonPlayer)s).getWorldName();
		else server  = "*";
		boolean ignoreperm = false;
		Object arg1 = args.get(0).getRight();
		Object arg2 = args.get(1).getRight();
		Object arg3 = args.get(2).getRight();
		if(arg1 != null && arg1 instanceof ServerData) server = ((ServerData) arg1).getName();
		String nick = arg2 != null ? null : s.getDisplayName();
		if(arg3 != null) ignoreperm = true;
		ChatUtil.clearChat(server, ignoreperm);
		ChatUtil.sendClearMessage(server, nick);
		s.sendMessage(MiscUtil.parseComponent(Config.PREFIX.getString()+(server.equals("*")? Config.COMMANDS_CLEAR_DONE_GLOBAL.getString() : Config.COMMANDS_CLEAR_DONE.getString().replace("%server%", server)), false));
		return true;
	}
	
	@Override
	public List<Pair<ArgumentType, Boolean>> getArguments() {
		List<Pair<ArgumentType, Boolean>> list = new ArrayList<Pair<ArgumentType, Boolean>>();
		list.add(new Pair<>(ArgumentType.SERVER, false)); //SERVER
		list.add(new Pair<>(ArgumentType.STATIC, false)); //-permignore -ignore
		list.add(new Pair<>(ArgumentType.STATIC, false)); //-incognito -inc -i
		return list;
	}

}
