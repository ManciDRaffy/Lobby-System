package de.laudytv.lobbysystem.automessage;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ChatPlayer {

    private final static LobbySystem plugin = LobbySystem.getPlugin();
    private static BukkitRunnable runnable = null;


    public static void run() {
        cancel();
        if (plugin.amData.getEnabled("chat")) {

            long timer = 20L * plugin.amData.getTimer("chat");
            List<String> messages = plugin.amData.getMessages("chat");

            runnable = new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(Format.format(messages.get(i))));
                    i++;
                    if (i == messages.size()) i = 0;
                }
            };

            runnable.runTaskTimer(plugin, 0, timer);
        }
    }

    public static void cancel() {
        if (runnable != null)
            runnable.cancel();
    }
}

