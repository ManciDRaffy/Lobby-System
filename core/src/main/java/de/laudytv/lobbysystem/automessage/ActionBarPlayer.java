package de.laudytv.lobbysystem.automessage;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.actionbar.ActionBarWrapper;
import de.laudytv.lobbysystem.format.Format;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ActionBarPlayer {

    private final static LobbySystem plugin = LobbySystem.getPlugin();
    private static BukkitRunnable runnable = null, steady = null;

    public static void run() {
        cancel();

        if (plugin.amData.getEnabled("action")) {

            long timer = 20L * plugin.amData.getTimer("action");
            List<String> messages = plugin.amData.getMessages("action");

            runnable = new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    steady = new BukkitRunnable() {
                        long steady = timer;

                        @Override
                        public void run() {
                            Bukkit.getOnlinePlayers().forEach(player -> ActionBarWrapper.sendActionBar(player, Format.format(messages.get(i))));
                            steady--;
                            if (steady == 0) cancel();
                        }
                    };

                    steady.runTaskTimer(plugin, 0, 1);

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
        if (steady != null)
            steady.cancel();
    }
}
