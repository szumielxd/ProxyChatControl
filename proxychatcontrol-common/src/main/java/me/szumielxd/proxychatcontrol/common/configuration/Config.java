package me.szumielxd.proxychatcontrol.common.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.Configuration;
import org.simpleyaml.configuration.MemorySection;
import org.simpleyaml.configuration.file.YamlConfiguration;

import me.szumielxd.proxychatcontrol.common.ProxyChatControl;
import me.szumielxd.proxychatcontrol.common.utils.MiscUtil;

public enum Config {
	
	PREFIX("prefix", "&2[&aChatControl&2] &8 &7"),
	CONSOLE("console", "CONSOLE"),
	COMMAND_NAME("command", "bungeechatcontrol"),
	COMMAND_ALIASES("aliases", Arrays.asList("bcc", "bchc", "chc")),


	SETTINGS_USE_LUCKPERMS_DISPLAYNAME("settings.use-luckperms-displayname", true),
	SETTINGS_REPORT_TIMEOUT("settings.report-timeout", 120),


	LANGUAGE_MISC_ON("language.misc-on", "&awlaczono"),
	LANGUAGE_MISC_OFF("language.misc-off", "&4wylaczono"),
	LANGUAGE_MISC_TRUE("language.misc-true", "&atrue"),
	LANGUAGE_MISC_FALSE("language.misc-false", "&4false"),
	LANGUAGE_CHAT_DENY_MESSAGE("language.chat-deny-message", "&cAktualnie chat jest wylaczony"),
	LANGUAGE_CHAT_CLEAR_INFO("language.chat-clear-info", "&7Chat zostal wyczyszczony przez &b%sender%"),
	LANGUAGE_CHAT_CLEAR_INFO_INCOGNITO("language.chat-clear-info-incognito", "&7Chat zostal wyczyszczony"),


	COMMANDS_ERROR("commands.error", "&4Cos poszlo baaaaaardzo zle. Powiadom admina!"),
	COMMANDS_PERMISSION_ERROR("commands.permission-error", "&cNie masz do tego dostepu c:"),
	COMMANDS_PARSE_ERROR("commands.parse-error", "&cZnaleziono \"%arg%\", a wymagano typ: %type%"),
	COMMANDS_ARGTYPES_TEXT("commands.arg-types.text", "tekst"),
	COMMANDS_ARGTYPES_INTEGER("commands.arg-types.integer", "l.calk."),
	COMMANDS_ARGTYPES_NUMBER("commands.arg-types.number", "liczba"),
	COMMANDS_ARGTYPES_SERVER("commands.arg-types.server", "serwer"),
	COMMANDS_ARGTYPES_BOOLEAN("commands.arg-types.boolean", "boolean"),
	COMMANDS_HELP_ALIASES("commands.help.aliases", Arrays.asList("h")),
	COMMANDS_HELP_DESCRIPTION("commands.help.description", "Zobacz wszystkie komendy"),
	COMMANDS_HELP_HEADER("commands.help.header", "&8&m-------------------&r &2&lChat&aControl&r &8&m-------------------&r"),
	COMMANDS_HELP_FOOTER("commands.help.footer", "&8&m---------------------------------------------------&r"),
	COMMANDS_HELP_FORMAT("commands.help.format", "  &a/%command%&7%args% &8- &f%description%"),
	COMMANDS_HELP_CLICK("commands.help.click", "/%command%%args%"),
	COMMANDS_HELP_HOVER("commands.help.hover", Arrays.asList("&bAliasy: %aliases%", "&aKliknij aby wkleic komende")),
	COMMANDS_LIST_ALIASES("commands.list.aliases", Arrays.asList("servers", "status", "l")),
	COMMANDS_LIST_DESCRIPTION("commands.list.description", "Wyswietl status chatu na trybach"),
	COMMANDS_LIST_START("commands.list.start", "&fDostepne serwery: "),
	COMMANDS_LIST_SEPARATOR("commands.list.separator", "&7, "),
	COMMANDS_LIST_ENABLED("commands.list.enabled", "&a"),
	COMMANDS_LIST_DISABLED("commands.list.disabled", "&c"),
	COMMANDS_LIST_CLICK("commands.list.click", "/bungeechatcontrol toggle %server%"),
	COMMANDS_LIST_HOVER("commands.list.hover", Arrays.asList("&7chat: %enabled%", "&7online: &b%online%", "&aKliknij aby zmienic")),
	COMMANDS_CLEAR_ALIASES("commands.clear.aliases", Arrays.asList("cl")),
	COMMANDS_CLEAR_DESCRIPTION("commands.clear.description", "Wyczysc chat"),
	COMMANDS_CLEAR_DONE("commands.clear.done", "&aPomyslnie wyczyszczono chat na trybie &b%server%"),
	COMMANDS_CLEAR_DONE_GLOBAL("commands.clear.done-global", "&aPomyslnie wyczyszczono chat &bglobalnie"),
	COMMANDS_TOGGLE_ALIASES("commands.toggle.aliases", Arrays.asList("t")),
	COMMANDS_TOGGLE_DESCRIPTION("commands.toggle.description", "Przelacz blokade chatu"),
	COMMANDS_TOGGLE_INFO_ON("commands.toggle.info-on", "&aChat zostal wlaczony przez %sender%"),
	COMMANDS_TOGGLE_INFO_ON_INCOGNITO("commands.toggle.info-on-incognito", "&aChat zostal wlaczony"),
	COMMANDS_TOGGLE_INFO_OFF("commands.toggle.info-off", "&cChat zostal wylaczony przez %sender%"),
	COMMANDS_TOGGLE_INFO_OFF_INCOGNITO("commands.toggle.info-off-incognito", "&cChat zostal wylaczony"),
	COMMANDS_TOGGLE_DONE_ON("commands.toggle.done-on", "&7Pomyslnie &awlaczono &7chat na trybie &b%server%"),
	COMMANDS_TOGGLE_DONE_ON_GLOBAL("commands.toggle.done-on-global", "&7Pomyslnie &awlaczono &7chat &bglobalnie"),
	COMMANDS_TOGGLE_DONE_OFF("commands.toggle.done-off", "&7Pomyslnie &4wylaczono &7chat na trybie &b%server%"),
	COMMANDS_TOGGLE_DONE_OFF_GLOBAL("commands.toggle.done-off-global", "&7Pomyslnie &4wylaczono &7chat &bglobalnie"),
	COMMANDS_TOGGLE_ALREADY_ON("commands.toggle.already-on", "&cChat jest juz wlaczony"),
	COMMANDS_TOGGLE_ALREADY_OFF("commands.toggle.already-off", "&cChat jest juz wylaczony"),
	COMMANDS_RELOAD_ALIASES("commands.reload.aliases", Arrays.asList("rl")),
	COMMANDS_RELOAD_DESCRIPTION("commands.reload.description", "Przeladuj plugin"),
	COMMANDS_RELOAD_COMPLETED("commands.reload.completed", "&aPomyslnie przeladowano plugin"),
	COMMANDS_RELOAD_FAILED("commands.reload.failed", "&4Wystapil blad w trakcie przeladowywania. Wylaczam, zobacz konsole"),
	;
	
	
	
	//////////////////////////////////////////////////////////////////////
	
	private final String path;
	private List<String> texts;
	private String text;
	private int number;
	private boolean bool;
	private Map<String, ?> map;
	private boolean colored = false;
	private Class<?> type;
	
	
	private Config(String path, String text) {
		this(path, text, false);
	}
	private Config(String path, String text, boolean colored) {
		this.path = path;
		this.colored = colored;
		setValue(text);
	}
	private Config(String path, List<String> texts) {
		this(path, texts, false);
	}
	private Config(String path, List<String> texts, boolean colored) {
		this.path = path;
		this.colored = colored;
		setValue(texts);
	}
	private Config(String path, int number) {
		this.path = path;
		setValue(number);
	}
	private Config(String path, boolean bool) {
		this.path = path;
		setValue(bool);
	}
	private Config(String path, Map<String, Object> valueMap) {
		this.path = path;
		setValue(valueMap);
	}
	
	
	
	//////////////////////////////////////////////////////////////////////
	
	public void setValue(String text) {
		this.type = String.class;
		this.text = text;
		this.texts = new ArrayList<>(Arrays.asList(new String[] { this.text }));
		this.number = text.length();
		this.bool = !text.isEmpty();
		this.map = new HashMap<>();
	}
	public void setValue(List<String> texts) {
		this.type = String[].class;
		this.text = String.join(", ", texts);
		this.texts = texts;
		this.number = texts.size();
		this.bool = !texts.isEmpty();
		this.map = texts.stream().collect(Collectors.toMap(v -> Integer.toString(texts.indexOf(v)), v -> v));
	}
	public void setValue(int number) {
		this.type = Integer.class;
		this.text = Integer.toString(number);
		this.texts = new ArrayList<>(Arrays.asList(new String[] { this.text }));
		this.number = number;
		this.bool = number > 0;
		this.map = new HashMap<>();
	}
	public void setValue(boolean bool) {
		this.type = Boolean.class;
		this.text = Boolean.toString(bool);
		this.texts = new ArrayList<>(Arrays.asList(new String[] { this.text }));
		this.number = bool? 1 : 0;
		this.bool = bool;
		this.map = new HashMap<>();
	}
	public <T> void setValue(Map<String, T> valueMap) {
		this.type = Map.class;
		this.text = valueMap.toString();
		this.texts = valueMap.values().stream().map(v -> v.toString()).collect(Collectors.toList());
		this.number = valueMap.size();
		this.bool = !valueMap.isEmpty();
		this.map = valueMap;
	}
	
	
	public String getString() {
		return this.text;
	}
	public String toString() {
		return this.text;
	}
	public List<String> getStringList() {
		return new ArrayList<>(this.texts);
	}
	public int getInt() {
		return this.number;
	}
	public boolean getBoolean() {
		return this.bool;
	}
	public Map<String, ?> getValueMap() {
		return this.map;
	}
	public boolean isColored() {
		return this.colored;
	}
	public Class<?> getType() {
		return this.type;
	}
	public String getPath() {
		return this.path;
	}
	
	
	
	//////////////////////////////////////////////////////////////////////
	
	public static void load(@NotNull File file, @NotNull ProxyChatControl plugin) {
		Objects.requireNonNull(plugin, "plugin cannot be null").getLogger().info("Loading configuration from '" + Objects.requireNonNull(file, "file cannot be null").getName() + "'");
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		try {
			if(!file.exists()) file.createNewFile();
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			if(loadConfig(config) > 0) config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int loadConfig(Configuration config) {
		int modify = 0;
		for (Config val : Config.values()) {
			if(!config.contains(val.getPath())) modify++;
			if (val.getType().equals(String.class)) {
				if (val.isColored())val.setValue(getColoredStringOrSetDefault(config, val.getPath(), val.getString()));
				else val.setValue(getStringOrSetDefault(config, val.getPath(), val.getString()));
			} else if (val.getType().equals(String[].class)) {
				if (val.isColored())val.setValue(getColoredStringListOrSetDefault(config, val.getPath(), val.getStringList()));
				else val.setValue(getStringListOrSetDefault(config, val.getPath(), val.getStringList()));
			} else if (val.getType().equals(Integer.class)) val.setValue(getIntOrSetDefault(config, val.getPath(), val.getInt()));
			else if (val.getType().equals(Boolean.class)) val.setValue(getBooleanOrSetDefault(config, val.getPath(), val.getBoolean()));
			else if (val.getType().equals(Map.class)) val.setValue(getMapOrSetDefault(config, val.getPath(), val.getValueMap()));
		}
		return modify;
	}
	
	/*private static <T> T getOrSetDefault(Configuration config, String path, T def) {
		SZChatFilterAPI.get().getPlugin().debug("Config::getOrSetDefault -> "+config.contains(path)+","+Map.class.isInstance(def)+","+!config.getSection(path).getKeys().isEmpty()+","+def.getClass().isInstance(config.get(path)));
		if (config.contains(path) && def.getClass().isInstance(config.get(path))) return config.get(path, def);
		config.set(path, def);
		return def;
	}*/
	
	@SuppressWarnings("unchecked")
	private static <T> Map<String,T> getMapOrSetDefault(Configuration config, String path, Map<String,T> def) {
		if (config.contains(path)) {
			return (Map<String, T>) ((MemorySection) config.getConfigurationSection(path)).getMapValues(false);
		}
		config.set(path, def);
		return def;
	}
	
	private static int getIntOrSetDefault(Configuration config, String path, int def) {
		if (config.contains(path)) return config.getInt(path);
		config.set(path, def);
		return def;
	}
	
	private static boolean getBooleanOrSetDefault(Configuration config, String path, boolean def) {
		if (config.contains(path)) return config.getBoolean(path);
		config.set(path, def);
		return def;
	}
	
	private static String getStringOrSetDefault(Configuration config, String path, String def) {
		if (config.contains(path)) return config.getString(path);
		config.set(path, def);
		return def;
	}
	
	private static String getColoredStringOrSetDefault(Configuration config, String path, String def) {
		String str = MiscUtil.translateAlternateColorCodes('&', getStringOrSetDefault(config, path, def.replace('§', '&')));
		return str;
	}
	
	private static ArrayList<String> getStringListOrSetDefault(Configuration config, String path, List<String> def) {
		if(config.contains(path)) return new ArrayList<>(config.getStringList(path));
		config.set(path, def);
		return new ArrayList<>(def);
	}
	
	private static ArrayList<String> getColoredStringListOrSetDefault(Configuration config, String path, List<String> def) {
		ArrayList<String> list = getStringListOrSetDefault(config, path, def.stream().map(str -> str.replace('§', '&')).collect(Collectors.toCollection(ArrayList::new)));
		return list.stream().map((str) -> MiscUtil.translateAlternateColorCodes('&', str))
				.collect(Collectors.toCollection(ArrayList::new));
	}

}
