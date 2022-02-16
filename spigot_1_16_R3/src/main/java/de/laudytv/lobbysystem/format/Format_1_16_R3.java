package de.laudytv.lobbysystem.format;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Format_1_16_R3 implements FormatMatcher {
    private final static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public String format(String msg) {
        Matcher match = pattern.matcher(msg);
        while (match.find()) {
            String color = msg.substring(match.start(), match.end());
            msg = msg.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(msg);
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
