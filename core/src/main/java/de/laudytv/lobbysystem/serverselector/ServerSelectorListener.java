package de.laudytv.lobbysystem.serverselector;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.logging.Level;

public class ServerSelectorListener implements Listener {

    static ServerSelectorMatcher serverSelectorMatcher;

    static {
        String packagename = ServerSelectorListener.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            serverSelectorMatcher = (ServerSelectorMatcher) Class.forName(packagename + "." + "ServerSelectorListener" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "ServerSelectorListener" + "_" + version + "\".");
        }
    }



    public static void giveServerSelectorItem(Player player) {
        serverSelectorMatcher.giveServerSelectorItem(player);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        serverSelectorMatcher.onMenuClick(e);
    }

    @EventHandler
    public void onServerSelector(PlayerInteractEvent e) {
        serverSelectorMatcher.onServerSelector(e);
    }

}
