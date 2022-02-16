package de.laudytv.lobbysystem.actionbar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class ActionBarWrapper {
    static ActionBarMatcher actionBarMatcher;

    static {
        String packagename = ActionBarWrapper.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            actionBarMatcher = (ActionBarMatcher) Class.forName(packagename + "." + "ActionBar" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "ActionBar" + "_" + version + "\".");
        }
    }

    public static void sendActionBar(Player player, String text) {
        actionBarMatcher.sendActionBar(player, text);
    }
}
