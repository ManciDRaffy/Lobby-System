package de.laudytv.lobbysystem.navigator;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class NavigatorListener implements Listener {

    private final LobbySystem plugin = LobbySystem.getPlugin();

    @EventHandler
    public void onNavigatorClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player p = (Player) event.getWhoClicked();
            if (event.getView().getTitle().equals(Format.format(plugin.warpData.getSettingsDisplayName("inventory")))) {
                event.setCancelled(true);
                event.setCancelled(true);
                int slot = event.getSlot();
                String warpName = plugin.warpData.getWarpBySlot(slot);
                if (warpName == null)
                    return;
                if (plugin.warpData.isWarpSet(warpName)) {
                    Player player = (Player) event.getWhoClicked();
                    Location loc = plugin.warpData.getLocation(warpName);
                    player.teleport(loc);
                }
            }
        }
    }

    @EventHandler
    public void onNavigatorItemClick(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().hasItemMeta()) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (Objects.requireNonNull(event.getItem().getItemMeta()).getDisplayName().equals(Format.format(plugin.warpData.getSettingsDisplayName("item")))
                        && event.getItem().getType().equals(plugin.warpData.getSettingsMaterial("item"))) {
                    Navigator nav = new Navigator(LobbySystem.getPlugin());
                    nav.openInventory(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onNavJoin(PlayerJoinEvent event) {
        Navigator.getNavigatorItem(event.getPlayer());
    }
}
