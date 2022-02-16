package de.laudytv.lobbysystem.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class BossBarWrapper {

    static BossBarMatcher bossBarMatcher;

    static {
        String packagename = BossBarWrapper.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            bossBarMatcher = (BossBarMatcher) Class.forName(packagename + "." + "BossBar" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "BossBar" + "_" + version + "\".");
        }
    }

    public static void spawnBar(Player player, String text, String color, String division) {
        bossBarMatcher.spawnBar(player, text, color, division);
    }

    public static void teleport(Player player) {
        bossBarMatcher.teleport(player);
    }

    public static void remove(Player player) {
        bossBarMatcher.remove(player);
    }

}
