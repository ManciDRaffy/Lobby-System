package de.laudytv.lobbysystem.automessage;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.bossbar.BossBarWrapper;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BossBarPlayer {

    private final static LobbySystem plugin = LobbySystem.getPlugin();
    private static BukkitRunnable runnable = null, teleport = null;

    public static void run() {
        cancel();

        if (plugin.amData.getEnabled("boss")) {

            long timer = 20L * plugin.amData.getTimer("boss");
            List<String> messages = plugin.amData.getMessages("boss");

            runnable = new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    String color = plugin.amData.getColor(messages.get(i));
                    String division = plugin.amData.getType(messages.get(i));
                    Bukkit.getOnlinePlayers().forEach(player -> BossBarWrapper.spawnBar(player, messages.get(i), color, division));
                    i++;
                    if (i == messages.size()) i = 0;
                }
            };

            runnable.runTaskTimer(plugin, 0, timer);

            teleport = new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(BossBarWrapper::teleport);
                }
            };

            teleport.runTaskTimer(plugin, 0, 1);
        }
    }

    public static void cancel() {
        if (runnable != null)
            runnable.cancel();
        if (teleport != null)
            teleport.cancel();
        Bukkit.getOnlinePlayers().forEach(BossBarWrapper::remove);
    }

}
