package de.laudytv.lobbysystem.doublejump;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DoubleJump implements Listener {

    /**
     * Updates when player is on ground
     *
     * @param player the player
     */
    private void groundUpdate(Player player) {
        Location loc = player.getLocation();
        loc = loc.subtract(0, 1, 0);

        Block block = loc.getBlock();
        if (!block.getType().isSolid()) return;

        player.setAllowFlight(true);

    }

    /**
     * Enables double jump for player on join
     *
     * @param event join event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.groundUpdate(event.getPlayer());
    }

    /**
     * Disables damage on fall
     *
     * @param event entity damage event
     */
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        event.setCancelled(true);
    }

    /**
     * Updates when player is moving
     *
     * @param event move event
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer().getAllowFlight()) return;
        this.groundUpdate(event.getPlayer());
    }

    /**
     * perform double jump
     *
     * @param event toggle flight event (double space)
     */
    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR)
            return;

        event.setCancelled(true);
        event.getPlayer().setAllowFlight(false);
        event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(1.6d).setY(1.0d));
    }
}
