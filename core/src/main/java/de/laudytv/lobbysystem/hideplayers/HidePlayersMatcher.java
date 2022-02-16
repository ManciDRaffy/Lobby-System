package de.laudytv.lobbysystem.hideplayers;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

import java.util.List;

public interface HidePlayersMatcher {

    void setHidden(Player player, boolean bool, LobbySystem plugin, List<Player> hidden);

    void hideAll(LobbySystem plugin, List<Player> hidden);

    void giveHiddenItem(Player player);

    void giveShownItem(Player player);
}
