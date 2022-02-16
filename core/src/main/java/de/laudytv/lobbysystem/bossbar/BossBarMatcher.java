package de.laudytv.lobbysystem.bossbar;

import org.bukkit.entity.Player;

public interface BossBarMatcher {

    void spawnBar(Player player, String text, String color, String division);

    void remove(Player player);

    void teleport(Player player);

    void rename(Player player, String rename);
}
