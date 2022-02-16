package de.laudytv.lobbysystem.util;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface GetSkullMatcher {

    ItemStack get(String uuid, LobbySystem plugin);

    ItemStack getOffline();

    ItemStack getPane();

    ItemStack getFriendAdd();

    Material getSign();
}
