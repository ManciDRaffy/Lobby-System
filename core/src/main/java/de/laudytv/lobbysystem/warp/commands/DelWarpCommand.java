package de.laudytv.lobbysystem.warp.commands;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.sql.WarpSQLGetter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpCommand implements CommandExecutor {

    private final static LobbySystem plugin = LobbySystem.getPlugin();
    private final static String PREFIX = WarpSQLGetter.PREFIX;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.hasPermission(plugin.permissions.getPermission("warp-delete"))) {
            player.sendMessage(plugin.permissions.getPermissionMessage());
            return true;
        }
        if (args.length == 1) {
            if(!plugin.warpData.isWarpSet(args[0])) {
                player.sendMessage(PREFIX + "Warp is doesn't exist");
                return true;
            }
            plugin.warpData.removeWarp(args[0]);
            player.sendMessage(PREFIX + "Warp (§b" + args[0] + "§7) removed");
        } else {
            player.sendMessage(PREFIX + "Use: /§bdelwarp §7<§bwarp§7>");
        }
        return false;
    }
}
