package de.laudytv.lobbysystem.nick;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnNickCommand implements CommandExecutor {

    private LobbySystem plugin = LobbySystem.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only for players");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission(plugin.permissions.getPermission("nick"))) {
            player.sendMessage(plugin.permissions.getPermissionMessage());
            return true;
        }
        if (NickCommand.nickedPlayers.containsKey(player.getUniqueId())) {
            NickWrapper.setNick(player, NickCommand.nickedPlayers.get(player.getUniqueId()));
            player.sendMessage(NickCommand.PREFIX + "You got unnicked");
            NickCommand.nickedPlayers.remove(player.getUniqueId());
        } else {
            player.sendMessage(NickCommand.PREFIX + "You are not nicked");
        }

        return false;
    }
}
