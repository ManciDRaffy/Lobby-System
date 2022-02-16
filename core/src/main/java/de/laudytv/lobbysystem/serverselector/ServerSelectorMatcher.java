package de.laudytv.lobbysystem.serverselector;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ServerSelectorMatcher {

    void onMenuClick(InventoryClickEvent e);

    void onServerSelector(PlayerInteractEvent e);

    void giveServerSelectorItem(Player p);
}
