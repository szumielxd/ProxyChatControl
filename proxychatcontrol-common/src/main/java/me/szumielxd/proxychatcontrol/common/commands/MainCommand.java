package me.szumielxd.proxychatcontrol.common.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import me.szumielxd.proxychatcontrol.common.ProxyChatControl;
import me.szumielxd.proxychatcontrol.common.commands.subcommands.ClearCommand;
import me.szumielxd.proxychatcontrol.common.commands.subcommands.HelpCommand;
import me.szumielxd.proxychatcontrol.common.commands.subcommands.ListCommand;
import me.szumielxd.proxychatcontrol.common.commands.subcommands.ReloadCommand;
import me.szumielxd.proxychatcontrol.common.commands.subcommands.ToggleChatCommand;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.data.Pair;
import me.szumielxd.proxychatcontrol.common.objects.CommonSender;
import me.szumielxd.proxychatcontrol.common.utils.MiscUtil;

public class MainCommand extends CommonCommand {
	
	private final @NotNull ProxyChatControl plugin;

	public MainCommand(@NotNull ProxyChatControl plugin) {
		super(Config.COMMAND_NAME.getString(), "bungeechatcontrol.command.help", Config.COMMAND_ALIASES.getStringList().toArray(new String[0]));
		this.plugin = plugin;
		new ClearCommand("clear", Config.COMMANDS_CLEAR_DESCRIPTION.getString(), Config.COMMANDS_CLEAR_ALIASES.getString());
		new ToggleChatCommand("toggle", Config.COMMANDS_TOGGLE_DESCRIPTION.getString(), Config.COMMANDS_TOGGLE_ALIASES.getString());
		new HelpCommand("", Config.COMMANDS_HELP_DESCRIPTION.getString(), Config.COMMANDS_HELP_ALIASES.getString());
		new ListCommand("list", Config.COMMANDS_LIST_DESCRIPTION.getString(), Config.COMMANDS_LIST_ALIASES.getString());
		new ReloadCommand("reload", Config.COMMANDS_RELOAD_DESCRIPTION.getString(), Config.COMMANDS_RELOAD_ALIASES.getString());
	}

	@Override
	public void execute(CommonSender s, String[] args) {
		
		if(args.length == 0) args = new String[] {""};
		String[] subargs = Arrays.copyOfRange(args, 1, args.length);
		Map<String, SubCommand> subcommands = SubCommand.getSubCommands();
		if(subcommands == null || subcommands.size() == 0) {
			s.sendMessage(MiscUtil.parseComponent(Config.COMMANDS_ERROR.getString(), false));
			return;
		}
		SubCommand last = null;
		for(SubCommand subcmd : subcommands.values()) {
			if(subcmd.match("")) last = subcmd;
			if(subcmd.match(args[0])) {
				tryRun(subcmd, s, subargs);
				return;
			}
		}
		if(last == null) return;
		tryRun(last, s, subargs);
		return;
	}
	
	
	private void tryRun(SubCommand subcmd, CommonSender s, String[] args) {
		if(subcmd.hasPermission(s)) {
			try {
				List<Pair<ArgumentType, Object>> subargs = subcmd.parseValues(args);
				try {
					if(!subcmd.execute(s, subargs)) s.sendMessage(MiscUtil.parseComponent(Config.COMMANDS_ERROR.getString(), false));
				} catch (Throwable e) {
					e.printStackTrace();
					s.sendMessage(MiscUtil.parseComponent(Config.COMMANDS_ERROR.getString(), false));
				}
			} catch (IllegalArgumentException e) {
				String[] arr = e.getMessage().split(",");
				if(arr.length != 2) s.sendMessage(MiscUtil.parseComponent(Config.COMMANDS_ERROR.getString(), false));
				Integer i = Integer.valueOf(arr[0]);
				if(i == null) s.sendMessage(MiscUtil.parseComponent(Config.COMMANDS_ERROR.getString(), false));
				ArgumentType type = ArgumentType.valueOf(arr[1]);
				String msg = type.equals(ArgumentType.STATIC) ? Config.COMMANDS_PARSE_ERROR.getString().replace("%arg%", args.length>i? args[i] : "").replace("%type%", subcmd.getArgsString().split(" ")[i])
						: Config.COMMANDS_PARSE_ERROR.getString().replace("%arg%", args.length>i? args[i] : "").replace("%type%", type.getDisplay());
				s.sendMessage(MiscUtil.parseComponent(msg, false));
			}
		} else {
			s.sendMessage(MiscUtil.parseComponent(Config.COMMANDS_PERMISSION_ERROR.getString(), false));
		}
	}
		

}
