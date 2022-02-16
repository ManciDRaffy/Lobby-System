package de.laudytv.lobbysystem.vanish;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface IVanish {

    /**
     * Hides the player depending on version
     *
     * @param player player to hide
     * @param hide   hide or show
     */
    void hide(Player player, boolean hide);

    /**
     * hides all players in list
     *
     * @param hiddenPlayers list of uuids of players
     */
    void hideAll(List<UUID> hiddenPlayers);

}
