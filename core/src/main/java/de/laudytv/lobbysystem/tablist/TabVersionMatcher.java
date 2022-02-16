package de.laudytv.lobbysystem.tablist;

import org.bukkit.entity.Player;

public interface TabVersionMatcher {

    void registerTeam(Player player, String prefix, String scolor, String suffix, int level);

    void setTablist(Player player);
}
