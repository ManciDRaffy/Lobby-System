package de.laudytv.lobbysystem.automessage;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoMessageCommand implements CommandExecutor {

    private final LobbySystem plugin = LobbySystem.getPlugin();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.permissions.getPermission("automessage"))) {
            sender.sendMessage(plugin.permissions.getPermissionMessage());
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("Please use this command as a player");
            return true;
        }

        Player player = (Player) sender;
        String PREFIX = "§a§lAM §8» §7";

        if (args.length == 3) {

            if (args[0].equalsIgnoreCase("boss")) {
                if (args[1].equalsIgnoreCase("enabled")) {
                    boolean bool = Boolean.parseBoolean(args[2]);
                    plugin.amData.setEnabled("boss", bool);
                    if (bool) {
                        player.sendMessage(PREFIX + "BossBar §aenabled");
                        BossBarPlayer.run();
                    } else {
                        player.sendMessage(PREFIX + "BossBar §cdisabled");
                        BossBarPlayer.run();
                    }
                } else if (args[1].equalsIgnoreCase("timer")) {
                    int timer = Integer.parseInt(args[2]);
                    plugin.amData.setTimer("boss", timer);
                    BossBarPlayer.run();
                    player.sendMessage(PREFIX + "Changed BossBar Timer to§8: §a" + timer);
                }

            } else if (args[0].equalsIgnoreCase("chat")) {
                if (args[1].equalsIgnoreCase("enabled")) {
                    boolean bool = Boolean.parseBoolean(args[2]);
                    plugin.amData.setEnabled("chat", bool);
                    if (bool) {
                        player.sendMessage(PREFIX + "Chat §aenabled");
                        ChatPlayer.run();
                    } else {
                        player.sendMessage(PREFIX + "Chat §cdisabled");
                        ChatPlayer.run();
                    }
                } else if (args[1].equalsIgnoreCase("timer")) {
                    int timer = Integer.parseInt(args[2]);
                    plugin.amData.setTimer("chat", timer);
                    ChatPlayer.run();
                    player.sendMessage(PREFIX + "Changed Chat Timer to§8: §a" + timer);
                }

            } else if (args[0].equalsIgnoreCase("action")) {
                if (args[1].equalsIgnoreCase("enabled")) {
                    boolean bool = Boolean.parseBoolean(args[2]);
                    plugin.amData.setEnabled("action", bool);
                    if (bool) {
                        player.sendMessage(PREFIX + "ActionBar §aenabled");
                        ActionBarPlayer.run();
                    } else {
                        player.sendMessage(PREFIX + "ActionBar §cdisabled");
                        ActionBarPlayer.run();
                    }
                } else if (args[1].equalsIgnoreCase("timer")) {
                    int timer = Integer.parseInt(args[2]);
                    plugin.amData.setTimer("action", timer);
                    ActionBarPlayer.run();
                    player.sendMessage(PREFIX + "Changed ActionBar Timer to§8: §a" + timer);
                }
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            BossBarPlayer.run();
            ActionBarPlayer.run();
            ChatPlayer.run();
            player.sendMessage(PREFIX + "All auto message settings reloaded");
        } else {
            sender.sendMessage(PREFIX + "Use: /automessage <boss/action/chat/reload> <enabled/timer> <value>");
            return true;
        }
        return false;
    }
}
