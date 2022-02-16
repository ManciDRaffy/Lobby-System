package de.laudytv.lobbysystem.util;

import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Checks {

    /**
     * @param player
     * @param permissionName
     * @param sendMessage
     * @return
     */
    public static boolean checkPermission(Player player, String permissionName, boolean sendMessage) {

        LobbySystem plugin = LobbySystem.getPlugin();
        String message = plugin.lang.getMessage(plugin.lang.getLanguage(player), "permission-denied");
        message = Format.format(message);
        String permission = plugin.permissions.getPermission(permissionName);

        // check if player has permission
        if (!player.hasPermission(permission)) {
            if (sendMessage) player.sendMessage(message);
            return false;
        }
        return true;
    }

    /**
     * Checks if sender is a player
     *
     * @param sender command sender
     * @return is sender a player
     */
    public static boolean checkIfPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only for players.");
            return false;
        } else {
            return true;
        }
    }
}
