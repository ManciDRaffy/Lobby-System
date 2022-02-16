package de.laudytv.lobbysystem.hideplayers;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.util.ItemBuilder_1_16_R3;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class HidePlayersManager_1_16_R3 implements HidePlayersMatcher {

    private final static LobbySystem plugin = LobbySystem.getPlugin();

    public void setHidden(Player player, boolean bool, LobbySystem plugin, List<Player> hidden) {
        if (bool)
            HidePlayersManager.addToHidden(player);
        else
            HidePlayersManager.removeFromHidden(player);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer != player)
                if (bool) {
                    player.hidePlayer(plugin, onlinePlayer);
                } else {
                    player.showPlayer(plugin, onlinePlayer);
                }
        }
    }

    public void hideAll(LobbySystem plugin, List<Player> hidden) {
        for (Player p : hidden) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (p != onlinePlayer) {
                    p.hidePlayer(plugin, onlinePlayer);
                }
            }
        }
    }

    public void giveHiddenItem(Player p) {
        int slot = plugin.hideData.getSlot("hidden");
        String displayName = Format.format(plugin.hideData.getDisplayName("hidden"));
        Material material = plugin.hideData.getMaterial("hidden");
        List<String> loreList = plugin.hideData.getLore("hidden");
        for (int i = 0; i < loreList.size(); i++)
            loreList.set(i, Format.format(loreList.get(i)));
        p.getInventory().setItem(slot, new ItemBuilder_1_16_R3(material).setDisplayname(displayName).setUnbreakable(true).setLore(loreList.toArray(new String[0])).build());
    }

    public void giveShownItem(Player p) {
        int slot = plugin.hideData.getSlot("shown");
        String displayName = Format.format(plugin.hideData.getDisplayName("shown"));
        Material material = plugin.hideData.getMaterial("shown");
        List<String> loreList = plugin.hideData.getLore("shown");
        for (int i = 0; i < loreList.size(); i++)
            loreList.set(i, Format.format(loreList.get(i)));
        p.getInventory().setItem(slot, new ItemBuilder_1_16_R3(material).setDisplayname(displayName).setUnbreakable(true).setLore(loreList.toArray(new String[0])).build());
    }


}
