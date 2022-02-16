package de.laudytv.lobbysystem.gamemode;

import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.util.Checks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

    private final String PREFIX = Format.format("&6&lGAMEMODE &8» &7");
    private final LobbySystem plugin = LobbySystem.getPlugin();
    private Player player;
    private String language;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Checks.checkIfPlayer(sender)) return true;
        player = (Player) sender;
        if (!Checks.checkPermission(player, "gamemode", true)) return true;

        language = plugin.lang.getLanguage(player);
        String creative = Format.format(plugin.lang.getMessage(language, "gamemode-creative"));
        String survival = Format.format(plugin.lang.getMessage(language, "gamemode-survival"));
        String spectator = Format.format(plugin.lang.getMessage(language, "gamemode-spectator"));
        String adventure = Format.format(plugin.lang.getMessage(language, "gamemode-adventure"));

        // /gmc command
        if (label.equalsIgnoreCase("gmc")) {
            if (args.length == 0) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(PREFIX + creative);
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                    return true;
                }
                if (target == player) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(PREFIX + creative);
                    return true;
                }
                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-creative-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-creative-to-sender"));
            } else {
                player.sendMessage(PREFIX + "/gmc [<player>]");
            }
            return true;
        }
        // /gms command
        if (label.equalsIgnoreCase("gms")) {
            if (args.length == 0) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(PREFIX + survival);
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                    return true;
                }
                if (target == player) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(PREFIX + survival);
                    return true;
                }
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-survival-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-survival-to-sender"));
            } else {
                player.sendMessage(PREFIX + "/gms [<player>]");
            }
            return true;
        }
        // /gmsp command
        if (label.equalsIgnoreCase("gmsp")) {
            if (args.length == 0) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(PREFIX + spectator);
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                    return true;
                }
                if (target == player) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(PREFIX + spectator);
                    return true;
                }
                target.setGameMode(GameMode.SPECTATOR);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-spectator-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-spectator-to-sender"));
            } else {
                player.sendMessage(PREFIX + "/gmsp [<player>]");
            }
            return true;
        }
        // /gma command
        if (label.equalsIgnoreCase("gma")) {
            if (args.length == 0) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(PREFIX + adventure);
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                    return true;
                }
                if (target == player) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(PREFIX + adventure);
                    return true;
                }
                target.setGameMode(GameMode.ADVENTURE);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-adventure-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-adventure-to-sender"));
            } else {
                player.sendMessage(PREFIX + "/gma [<player>]");
            }
            return true;
        }

        if (args.length == 1) {
            // GameMode Creative
            if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(PREFIX + creative);
                // GameMode Survival
            } else if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(PREFIX + survival);
                // GameMode Adventure
            } else if (args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(PREFIX + adventure);
                // GameMode Spectator
            } else if (args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(PREFIX + spectator);
            } else {
                player.sendMessage(PREFIX + "/gamemode <survival/creative/adventure/spectator> [<player>]");
            }
        } else if (args.length == 2) {
            //GameMode Creative
            if (args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("1")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                    return true;
                }
                if (target == player) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(PREFIX + creative);
                    return true;
                }
                target.setGameMode(GameMode.CREATIVE);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-creative-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-creative-to-sender"));
                // GameMode Survival
            } else if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                    return true;
                }
                if (target == player) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(PREFIX + survival);
                    return true;
                }
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-survival-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-survival-to-sender"));
                // GameMode Adventure
            } else if (args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("2")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                    return true;
                }
                if (target == player) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(PREFIX + adventure);
                    return true;
                }
                target.setGameMode(GameMode.ADVENTURE);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-adventure-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-adventure-to-sender"));
                // GameMode Spectator
            } else if (args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("3")) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                    return true;
                }
                if (target == player) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(PREFIX + spectator);
                    return true;
                }
                target.setGameMode(GameMode.SPECTATOR);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-spectator-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "gamemode-spectator-to-sender"));
            } else {
                player.sendMessage(PREFIX + "/gamemode <survival/creative/adventure/spectator> [<player>]");
            }
        } else {
            player.sendMessage(PREFIX + "/gamemode <survival/creative/adventure/spectator> [<player>]");
        }
        return false;
    }
}
