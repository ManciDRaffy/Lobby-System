package de.laudytv.lobbysystem.util;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class GetSkull {

    static GetSkullMatcher getSkullMatcher;

    static {
        String packagename = GetSkull.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            getSkullMatcher = (GetSkullMatcher) Class.forName(packagename + "." + "GetSkull" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "GetSkull" + "_" + version + "\".");
        }
    }

    public static ItemStack get(String uuid) {
        return getSkullMatcher.get(uuid, LobbySystem.getPlugin());
    }

    public static ItemStack getOffline() {
        return getSkullMatcher.getOffline();
    }

    public static ItemStack getPane() {
        return getSkullMatcher.getPane();
    }

    public static ItemStack getFriendAdd() {
        return getSkullMatcher.getFriendAdd();
    }

    public static Material getSign() {
        return getSkullMatcher.getSign();
    }
}
