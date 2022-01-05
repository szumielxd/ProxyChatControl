package me.szumielxd.proxychatcontrol.common.commands.subcommands;

import java.util.ArrayList;
import java.util.List;

import me.szumielxd.proxychatcontrol.common.ProxyChatControl;
import me.szumielxd.proxychatcontrol.common.ProxyChatControlProvider;
import me.szumielxd.proxychatcontrol.common.commands.ArgumentType;
import me.szumielxd.proxychatcontrol.common.commands.SubCommand;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.data.Pair;
import me.szumielxd.proxychatcontrol.common.objects.CommonSender;

public class ReloadCommand extends SubCommand {

	public ReloadCommand(String name, String descr, String... aliases) {
		super(name, descr, aliases);
	}

	@Override
	public boolean execute(CommonSender s, List<Pair<ArgumentType, Object>> args) {
		
		try {
			ProxyChatControl inst = ProxyChatControlProvider.get();
			inst.onDisable();
			inst.onEnable();
			s.sendMessage(Config.COMMANDS_RELOAD_COMPLETED.getString());
		} catch (Exception e) {
			e.printStackTrace();
			s.sendMessage(Config.COMMANDS_RELOAD_FAILED.getString());
		}
		
		return true;
	}
	
	public List<Pair<ArgumentType, Boolean>> getArguments(){
		return new ArrayList<Pair<ArgumentType, Boolean>>();
	}

}
