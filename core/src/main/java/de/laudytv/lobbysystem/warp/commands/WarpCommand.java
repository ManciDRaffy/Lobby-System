package de.laudytv.lobbysystem.warp.commands;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.sql.WarpSQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    private final static LobbySystem plugin = LobbySystem.getPlugin();
    private final static String PREFIX = WarpSQLGetter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.hasPermission(plugin.permissions.getPermission("warp-teleport"))) {
            player.sendMessage(plugin.permissions.getPermissionMessage());
            return true;
        }
        if (args.length == 1) {
            if (!plugin.warpData.isWarpSet(args[0])) {
                player.sendMessage(PREFIX + "Warp doesn't exist");
                return true;
            }
            Location loc = plugin.warpData.getLocation(args[0]);
            player.teleport(loc);
            player.sendMessage(PREFIX + "You were teleported to warp §b" + args[0]);
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(PREFIX + "Player doesn't exist");
                return true;
            }
            Location loc = plugin.warpData.getLocation(args[0]);
            target.teleport(loc);
            player.sendMessage(PREFIX + "You teleported §b" + target.getName() + " §7to §b" + args[0]);
            target.sendMessage(PREFIX + "You were teleported to warp §b" + args[0]);
        } else {
            player.sendMessage(PREFIX + "Use: /§bwarp §7<§bwarp§7> [<§bplayer§7>]");
        }
        return false;
    }
}
