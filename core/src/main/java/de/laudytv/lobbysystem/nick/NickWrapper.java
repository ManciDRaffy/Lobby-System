package de.laudytv.lobbysystem.nick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class NickWrapper {

    static NickMatcher nickMatcher;

    static {
        String packagename = NickWrapper.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            nickMatcher = (NickMatcher) Class.forName(packagename + "." + "Nick" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "Nick" + "_" + version + "\".");
        }
    }

    public static void setNick(Player player, String nick) {
        nickMatcher.setNick(player, nick);
    }



}
