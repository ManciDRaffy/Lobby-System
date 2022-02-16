package de.laudytv.lobbysystem.util;

import org.bukkit.entity.Player;

public class Placeholders {

    /**
     * Replaces placeholders for messages with player and target
     *
     * @param message to replace placeholders
     * @param player  sender
     * @param target  other player (target)
     * @return formatted string
     */
    public static String replacePlayerTarget(String message, Player player, Player target) {
        String replace = message;
        replace = replace.replaceAll("%player%", player.getName());
        replace = replace.replaceAll("%target%", target.getName());
        return replace;
    }
}
