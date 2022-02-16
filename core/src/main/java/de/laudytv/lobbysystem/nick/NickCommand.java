package de.laudytv.lobbysystem.nick;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.util.UUIDFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class NickCommand implements CommandExecutor {

    public static String PREFIX = "§f§lNICK §8» §7";
    public static HashMap<UUID, String> nickedPlayers = new HashMap<>();
    private LobbySystem plugin = LobbySystem.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.permissions.getPermission("nick"))) {
            sender.sendMessage(plugin.permissions.getPermissionMessage());
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only for players");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 1) {
            UUID uuid = UUIDFetcher.getUUID(args[0]);
            if (uuid == null) {
                player.sendMessage(PREFIX + "That player doesn't exist");
                return true;
            }
            if (!nickedPlayers.containsKey(player.getUniqueId()))
                nickedPlayers.put(player.getUniqueId(), player.getName());

            NickWrapper.setNick(player, args[0]);
            player.sendMessage(PREFIX + "You are now §a" + player.getName());
        }
        return false;
    }
}
