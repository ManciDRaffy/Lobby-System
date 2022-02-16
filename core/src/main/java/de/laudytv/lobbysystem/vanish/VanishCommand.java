package de.laudytv.lobbysystem.vanish;

import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.util.Checks;
import de.laudytv.lobbysystem.util.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VanishCommand implements CommandExecutor, Listener {

    private final LobbySystem plugin = LobbySystem.getPlugin();
    private final String PREFIX = Format.format("&7&lVANISH &8» &7");
    private VanishManager vanishManager;
    private Player player;
    private String language;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Checks.checkIfPlayer(sender)) return true;
        player = (Player) sender;
        if (!Checks.checkPermission(player, "vanish", true)) return true;

        vanishManager = plugin.getVanishManager();
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
            } else {
                String languageTarget = plugin.lang.getLanguage(target);
                if (!vanishManager.isHidden(target)) {
                    vanishManager.hide(target, true);
                    target.sendMessage(PREFIX + Placeholders.replacePlayerTarget(plugin.lang.getMessage(languageTarget, "vanish-enabled-to-target"), player, target));
                    player.sendMessage(PREFIX + Placeholders.replacePlayerTarget(plugin.lang.getMessage(language, "vanish-enabled-to-sender"), player, target));
                } else {
                    vanishManager.hide(target, false);
                    target.sendMessage(PREFIX + Placeholders.replacePlayerTarget(plugin.lang.getMessage(languageTarget, "vanish-disabled-to-target"), player, target));
                    player.sendMessage(PREFIX + Placeholders.replacePlayerTarget(plugin.lang.getMessage(language, "vanish-disabled-to-sender"), player, target));
                }
            }
        } else {
            player.sendMessage(PREFIX + "/vanish [<player>]");
        }


        return false;
    }

    /**
     * handles the sender
     */
    private void handle() {
        if (!vanishManager.isHidden(player)) {
            vanishManager.hide(player, true);
            player.sendMessage(PREFIX + plugin.lang.getMessage(language, "vanish-enabled"));
        } else {
            vanishManager.hide(player, false);
            player.sendMessage(PREFIX + plugin.lang.getMessage(language, "vanish-disabled"));
        }

    }

    /**
     * hides all players if a player join
     *
     * @param event join event
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        vanishManager.hideAll();
    }
}
