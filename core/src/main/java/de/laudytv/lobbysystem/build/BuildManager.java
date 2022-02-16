package de.laudytv.lobbysystem.build;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuildManager {

    private static final List<UUID> buildingPlayers = new ArrayList<>();

    /**
     * Sets a player into building mode.
     *
     * @param player player to change
     * @param bool   yes or no
     */
    public static void setBuilding(Player player, boolean bool) {
        if (bool)
            buildingPlayers.add(player.getUniqueId());
        else
            buildingPlayers.remove(player.getUniqueId());
    }

    /**
     * Checks if a player is building
     *
     * @param player player to check
     * @return is player in building mode
     */
    public static boolean isBuilding(Player player) {
        return buildingPlayers.contains(player.getUniqueId());
    }

    /**
     * @return List of players that are in building mode
     */
    public static List<UUID> isBuilding() {
        return buildingPlayers;
    }

}
