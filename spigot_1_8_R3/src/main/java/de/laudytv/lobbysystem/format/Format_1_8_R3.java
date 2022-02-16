package de.laudytv.lobbysystem.format;

import net.md_5.bungee.api.ChatColor;

public class Format_1_8_R3 implements FormatMatcher {

    public String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
