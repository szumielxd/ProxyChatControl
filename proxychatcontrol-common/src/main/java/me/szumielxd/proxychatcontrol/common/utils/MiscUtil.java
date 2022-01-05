package me.szumielxd.proxychatcontrol.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import me.szumielxd.proxychatcontrol.common.configuration.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class MiscUtil {
	
	
	private static char[] randomCharset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
	/**
	 * The special character which prefixes all chat colour codes. Use this if
	 * you need to dynamically convert colour codes from your custom format.
	 */
	public static final char COLOR_CHAR = '\u00A7';
	public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
	
	
	public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
		char[] b = textToTranslate.toCharArray();
		for ( int i = 0; i < b.length - 1; i++ )
		{
			if ( b[i] == altColorChar && ALL_CODES.indexOf( b[i + 1] ) > -1 )
			{
				b[i] = COLOR_CHAR;
				b[i + 1] = Character.toLowerCase( b[i + 1] );
			}
		}
		return new String( b );
	}
	
	
	public static String parseBool(boolean status) {
		return status? Config.LANGUAGE_MISC_TRUE.getString() : Config.LANGUAGE_MISC_FALSE.getString();
	}
	
	
	public static String parseStatus(boolean status) {
		return status? Config.LANGUAGE_MISC_ON.getString() : Config.LANGUAGE_MISC_OFF.getString();
	}
	
	
	public static Component deepReplace(Component comp, final String match, final Object replacement) {
		final String rep = replacement instanceof ComponentLike? LegacyComponentSerializer.legacyAmpersand().serialize(((ComponentLike)replacement).asComponent()) : String.valueOf(replacement);
		if (comp.clickEvent() != null) {
			ClickEvent click = comp.clickEvent();
			comp = comp.clickEvent(ClickEvent.clickEvent(click.action(), click.value().replace("{"+match+"}", rep)));
		}
		if (comp.insertion() != null) comp = comp.insertion(comp.insertion().replace("{"+match+"}", rep));
		ArrayList<Component> child = new ArrayList<>(comp.children());
		if (child != null && !child.isEmpty()) {
			child.replaceAll(c -> deepReplace(c, match, replacement));
			comp = comp.children(child);
		}
		return comp;
	}
	
	
	public static Component parseComponent(String unknown, boolean emptyAsNull) {
		if (unknown == null || (unknown.isEmpty() && emptyAsNull)) return null;
		try {
			Gson gson = new Gson();
			return GsonComponentSerializer.gson().deserializeFromTree(gson.fromJson(unknown, JsonObject.class));
		} catch (JsonSyntaxException e) {
			return LegacyComponentSerializer.legacySection().deserialize(translateAlternateColorCodes('&', unknown));
		}
	}
	
	
	public static String getPlainVisibleText(Component component) {
		Objects.requireNonNull(component, "component cannot be null");
		StringBuilder sb = new StringBuilder();
		if (component instanceof TextComponent) sb.append(((TextComponent) component).content());
		if (component.children() != null) component.children().forEach(c -> sb.append(getPlainVisibleText(c)));
		return sb.toString();
	}
	
	
	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder();
		new Random().ints(length, 0, randomCharset.length-1).forEach(cons ->{
			sb.append(randomCharset[cons]);
		});
		return sb.toString();
	}
	
	
	public static String parseOnlyDate(long timestamp) {
		return new SimpleDateFormat("dd-MM-yyyy").format(new Date(timestamp));
	}
	
	
	public static String parseOnlyTime(long timestamp) {
		return new SimpleDateFormat("HH:mm:ss").format(new Date(timestamp));
	}
	

}
