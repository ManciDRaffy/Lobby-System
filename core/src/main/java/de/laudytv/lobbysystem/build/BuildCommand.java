package de.laudytv.lobbysystem.build;

import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.util.Checks;
import de.laudytv.lobbysystem.util.LobbyItems;
import de.laudytv.lobbysystem.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    private final String PREFIX = Format.format("&6&lBUILD &8» &7");
    private final LobbySystem plugin = LobbySystem.getPlugin();
    private String language;
    private Player player;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // checks
        if (!Checks.checkIfPlayer(sender)) return true;
        player = (Player) sender;
        if (!Checks.checkPermission(player, "build", true)) return true;
        language = plugin.lang.getLanguage(player);

        if (args.length == 0) {
            handle();
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(PREFIX + plugin.lang.getMessage(language, "player-not-found"));
                return true;
            }
            String languageTarget = plugin.lang.getLanguage(target);

            if (player == target) {
                handle();
            } else {
                if (!BuildManager.isBuilding(target)) {
                    BuildManager.setBuilding(target, true);
                    target.setGameMode(GameMode.CREATIVE);
                    target.getInventory().clear();
                    target.sendMessage(PREFIX + Placeholders.replacePlayerTarget(plugin.lang.getMessage(languageTarget, "build-enabled-to-target"), player, target));
                    player.sendMessage(PREFIX + Placeholders.replacePlayerTarget(plugin.lang.getMessage(language, "build-enabled-to-sender"), player, target));
                } else {
                    BuildManager.setBuilding(target, false);
                    target.setGameMode(GameMode.SURVIVAL);
                    target.getInventory().clear();
                    LobbyItems.give(target);
                    target.sendMessage(PREFIX + Placeholders.replacePlayerTarget(plugin.lang.getMessage(languageTarget, "build-disabled-to-target"), player, target));
                    player.sendMessage(PREFIX + Placeholders.replacePlayerTarget(plugin.lang.getMessage(language, "build-disabled-to-sender"), player, target));
                }
            }
        }


        return false;
    }

    /**
     * void to handle player
     */
    private void handle() {
        if (!BuildManager.isBuilding(player)) {
            BuildManager.setBuilding(player, true);
            player.setGameMode(GameMode.CREATIVE);
            player.getInventory().clear();
            player.sendMessage(PREFIX + plugin.lang.getMessage(language, "build-enabled"));
        } else {
            BuildManager.setBuilding(player, false);
            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            LobbyItems.give(player);
            player.sendMessage(PREFIX + plugin.lang.getMessage(language, "build-disabled"));
        }
    }
}
