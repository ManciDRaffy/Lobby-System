package de.laudytv.lobbysystem.tablist;


import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class TabVersionWrapper {

    private static final LobbySystem plugin = LobbySystem.getPlugin();
    private static TabVersionMatcher versionMatcher;
    private static TabVersionWrapper instance;

    static {
        String packagename = TabVersionWrapper.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            versionMatcher = (TabVersionMatcher) Class.forName(packagename + ".TablistManager_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementaton for " + packagename + ".TablistManager_" + version + ".");
        }
    }

    public static TabVersionWrapper getInstance() {
        return instance;
    }


    public static void setTablist(Player player) {
        versionMatcher.setTablist(player);
    }
}
