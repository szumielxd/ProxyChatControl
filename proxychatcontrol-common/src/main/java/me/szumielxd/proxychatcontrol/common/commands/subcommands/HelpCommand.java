package me.szumielxd.proxychatcontrol.common.commands.subcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.szumielxd.proxychatcontrol.common.commands.ArgumentType;
import me.szumielxd.proxychatcontrol.common.commands.SubCommand;
import me.szumielxd.proxychatcontrol.common.configuration.Config;
import me.szumielxd.proxychatcontrol.common.data.Pair;
import me.szumielxd.proxychatcontrol.common.objects.CommonSender;
import me.szumielxd.proxychatcontrol.common.utils.MiscUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;

public class HelpCommand extends SubCommand {

	public HelpCommand(String name, String descr, String... aliases) {
		super(name, descr, aliases);
	}

	@Override
	public boolean execute(CommonSender s, List<Pair<ArgumentType, Object>> args) {
		
		if(!Config.COMMANDS_HELP_HEADER.getString().isEmpty()) s.sendMessage(MiscUtil.parseComponent(Config.COMMANDS_HELP_HEADER.getString(), false));
		
		Map<String, SubCommand> map = SubCommand.getSubCommands();
		if(map != null && !map.isEmpty())for(String cmd : map.keySet()) {
			String argstr = map.get(cmd).getArgsString();
			String cmdName = Config.COMMAND_NAME+" "+cmd;
			String description = map.get(cmd).getDescription();
			Component message = MiscUtil.parseComponent(Config.COMMANDS_HELP_FORMAT.getString().replace("%command%", cmdName)
					.replace("%aliases%", String.join(", ", map.get(cmd).getAliases()))
					.replace("%args%", argstr).replace("%description%", description)
					.replace("%sender%", s.getName()), false);
			if(!Config.COMMANDS_HELP_HOVER.getString().isEmpty()) {
				Component hover = MiscUtil.parseComponent(String.join("\n", Config.COMMANDS_HELP_HOVER.getString()).replace("%command%", cmdName)
						.replace("%aliases%", String.join(", ", map.get(cmd).getAliases()))
						.replace("%args%", argstr).replace("%description%", description)
						.replace("%sender%", s.getName()), false);
				message = message.hoverEvent(hover);
			}
			if(!Config.COMMANDS_HELP_CLICK.getString().isEmpty()) {
				String click = String.join("\n", Config.COMMANDS_HELP_CLICK.getString()).replace("%command%", cmdName).replace("%aliases%", String.join(", ", map.get(cmd).getAliases())).replace("%args%", argstr).replace("%description%", description).replace("%sender%", s.getName());
				message = message.clickEvent(ClickEvent.suggestCommand(click));
			}
			s.sendMessage(message);
		}
		
		if(!Config.COMMANDS_HELP_FOOTER.getString().isEmpty()) s.sendMessage(MiscUtil.parseComponent(Config.COMMANDS_HELP_FOOTER.getString(), false));
		
		return true;
	}
	
	@Override
	public List<Pair<ArgumentType, Boolean>> getArguments() {
		return new ArrayList<Pair<ArgumentType, Boolean>>();
	}

}
