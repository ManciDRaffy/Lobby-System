package de.laudytv.lobbysystem.listeners;

import de.laudytv.lobbysystem.build.BuildManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class LobbyListener implements Listener {


    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            ((Player) e.getEntity()).setHealth(20);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent e) {
        if (!BuildManager.isBuilding(e.getPlayer()))
            e.setCancelled(true);
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!BuildManager.isBuilding(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!BuildManager.isBuilding(e.getPlayer()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (!BuildManager.isBuilding(e.getPlayer()))
            e.setCancelled(true);
    }


    @EventHandler
    public void onInventoryInteract(InventoryInteractEvent e) {
        if (!BuildManager.isBuilding((Player) e.getWhoClicked()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!BuildManager.isBuilding(e.getPlayer()))
            e.setCancelled(true);

    }
}
