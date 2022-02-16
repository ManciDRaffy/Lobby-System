package de.laudytv.lobbysystem.format;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Format {

    static FormatMatcher formatMatcher;

    static {
        String packagename = Format.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            formatMatcher = (FormatMatcher) Class.forName(packagename + ".Format_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for " + version + ".");
        }
    }

    public static String format(String msg) {
        return formatMatcher.format(msg);
    }
}
