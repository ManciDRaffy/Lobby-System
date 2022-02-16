package de.laudytv.lobbysystem.commands;

import com.mojang.authlib.GameProfile;
import de.laudytv.lobbysystem.nick.NickWrapper;
import de.laudytv.lobbysystem.util.GameProfileBuilder;
import de.laudytv.lobbysystem.util.UUIDFetcher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.getName().equals("CreepertimeHD")) {
            sender.sendMessage("§cThis command is only for developers");
            return true;
        }
        Player player = (Player) sender;

        // DEBUG CODE

        if (args.length == 1) {
            UUID uuid = UUIDFetcher.getUUID(args[0]);
            GameProfile gameProfile = GameProfileBuilder.fetch(UUIDFetcher.getUUID(args[0]));
            NickWrapper.setNick(player, args[0]);
        }


        return false;
    }
}
