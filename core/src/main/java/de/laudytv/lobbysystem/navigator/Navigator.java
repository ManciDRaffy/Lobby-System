package de.laudytv.lobbysystem.navigator;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.logging.Level;

public class Navigator {

    static NavigatorMatcher navigatorMatcher;

    static {
        String packagename = Navigator.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            navigatorMatcher = (NavigatorMatcher) Class.forName(packagename + "." + "Navigator" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "Navigator" + "_" + version + "\".");
        }
    }

    private final LobbySystem plugin;
    private Inventory inventory;

    public Navigator(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public static void getNavigatorItem(Player player) {
        navigatorMatcher.getNavigatorItem(player);
    }

    public void openInventory(Player player) {
        String title = plugin.warpData.getSettingsDisplayName("inventory");
        title = Format.format(title);
        int rows = plugin.warpData.getSettingsRows("inventory");
        inventory = Bukkit.createInventory(null, 9 * rows, title);
        List<String> ids = plugin.warpData.getIDs();
        navigatorMatcher.initializeItems(inventory, ids);
        player.openInventory(inventory);
    }
}
