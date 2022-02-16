package de.laudytv.lobbysystem.vanish;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Vanish_1_8_R3 implements IVanish {

    /**
     * hides a player (1.8.8)
     *
     * @param player player to hide
     * @param hide   hide or show
     */
    @Override
    public void hide(Player player, boolean hide) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer != player) {
                if (hide)
                    onlinePlayer.hidePlayer(player);
                else
                    onlinePlayer.showPlayer(player);
            }
        }
    }

    /**
     * hides all players in list (1.8.8)
     *
     * @param hiddenPlayers list of uuids of players
     */
    @Override
    public void hideAll(List<UUID> hiddenPlayers) {
        if (hiddenPlayers == null) return;
        for (UUID uuid : hiddenPlayers) {
            Player player = Bukkit.getPlayer(uuid);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                if (player != onlinePlayer)
                    onlinePlayer.hidePlayer(player);
        }

    }
}
