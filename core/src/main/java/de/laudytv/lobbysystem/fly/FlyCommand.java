package de.laudytv.lobbysystem.fly;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.util.Checks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    private final LobbySystem plugin = LobbySystem.getPlugin();
    private final String PREFIX = Format.format("&9&lFLY &8» &7");
    private String language;
    private Player player;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Checks.checkIfPlayer(sender)) return true;
        player = (Player) sender;
        if (!Checks.checkPermission(player, "fly", true)) return true;
        language = plugin.lang.getLanguage(player);

        if (args.length == 0) {
            handle();
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                return true;
            }
            if (target == player) {
                handle();
                return true;
            }
            if (!target.getAllowFlight()) {
                target.setAllowFlight(true);
                target.teleport(player.getLocation().add(0, 0.1, 0));
                target.setFlying(true);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "fly-enabled-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "fly-enabled-to-sender"));
            } else {
                target.setAllowFlight(false);
                target.setFlying(false);
                target.sendMessage(PREFIX + plugin.lang.getMessage(language, "fly-disabled-to-target"));
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "fly-disabled-to-sender"));
            }
        } else {
            player.sendMessage(PREFIX + "/fly [<player>]");
        }
        return false;
    }


    /**
     * handles the sender
     */
    public void handle() {
        if (!player.getAllowFlight()) {
            player.setAllowFlight(true);
            player.teleport(player.getLocation().add(0, 0.1, 0));
            player.setFlying(true);
            player.sendMessage(PREFIX + plugin.lang.getMessage(language, "fly-enabled"));
        } else {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(PREFIX + plugin.lang.getMessage(language, "fly-disabled"));
        }
    }
}
