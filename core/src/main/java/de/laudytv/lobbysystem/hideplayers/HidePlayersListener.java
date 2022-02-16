package de.laudytv.lobbysystem.hideplayers;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class HidePlayersListener implements Listener {

    private static final LobbySystem plugin = LobbySystem.getPlugin();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (HidePlayersManager.isHidden(e.getPlayer()))
            HidePlayersManager.giveHiddenItem(e.getPlayer());
        else
            HidePlayersManager.giveShownItem(e.getPlayer());
        HidePlayersManager.hideAll();
    }


    @EventHandler
    public void onHideClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().hasItemMeta()) {
                if (Objects.requireNonNull(e.getItem().getItemMeta()).getDisplayName().equals(Format.format(plugin.hideData.getDisplayName("shown")))) {
                    HidePlayersManager.setHidden(e.getPlayer(), true);
                    HidePlayersManager.giveHiddenItem(e.getPlayer());
                } else if (e.getItem().getItemMeta().getDisplayName().equals(Format.format(plugin.hideData.getDisplayName("hidden")))) {
                    HidePlayersManager.setHidden(e.getPlayer(), false);
                    HidePlayersManager.giveShownItem(e.getPlayer());
                }
            }
        }
    }
}
