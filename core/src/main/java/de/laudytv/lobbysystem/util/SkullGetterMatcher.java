package de.laudytv.lobbysystem.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface SkullGetterMatcher {

    ItemStack getSkull(Player player, boolean displayname);
}
