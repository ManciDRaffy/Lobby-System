package de.laudytv.lobbysystem.hideplayers;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class HidePlayersManager {

    static HidePlayersMatcher hidePlayersMatcher;
    private static List<Player> hidden;

    static {
        String packagename = HidePlayersManager.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            hidePlayersMatcher = (HidePlayersMatcher) Class.forName(packagename + "." + "HidePlayersManager" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "HidePlayersManager" + "_" + version + "\".");
        }
    }

    public HidePlayersManager(LobbySystem plugin) {
        hidden = new ArrayList<>();
    }

    public static List<Player> isHidden() {
        return hidden;
    }

    public static void addToHidden(Player player) {
        hidden.add(player);
    }

    public static void removeFromHidden(Player player) {
        hidden.remove(player);
    }

    public static boolean isHidden(Player player) {
        return hidden.contains(player);
    }

    public static void hideAll() {
        hidePlayersMatcher.hideAll(LobbySystem.getPlugin(), hidden);
    }

    public static void setHidden(Player player, boolean bool) {
        hidePlayersMatcher.setHidden(player, bool, LobbySystem.getPlugin(), hidden);
    }

    public static void giveHiddenItem(Player player) {
        hidePlayersMatcher.giveHiddenItem(player);
    }

    public static void giveShownItem(Player player) {
        hidePlayersMatcher.giveShownItem(player);
    }

}
