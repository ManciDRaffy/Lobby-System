package de.laudytv.lobbysystem.listeners;

import de.laudytv.lobbysystem.friends.FriendGUI;
import de.laudytv.lobbysystem.serverselector.ServerSelectorListener;
import de.laudytv.lobbysystem.tablist.TabVersionWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    private TabVersionWrapper tabVersionWrapper;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        Player player = e.getPlayer();
        player.getInventory().clear();
        TabVersionWrapper.setTablist(player);
        ServerSelectorListener.giveServerSelectorItem(player);
        FriendGUI.giveFriendItem(player);
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
    }
}
