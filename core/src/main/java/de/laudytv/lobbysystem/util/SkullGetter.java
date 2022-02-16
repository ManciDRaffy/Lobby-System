package de.laudytv.lobbysystem.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class SkullGetter {

    static SkullGetterMatcher skullGetterMatcher;

    static {
        String packagename = SkullGetter.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            skullGetterMatcher = (SkullGetterMatcher) Class.forName(packagename + "." + "SkullGetterMatcher" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "SkullGetterMatcher" + "_" + version + "\".");
        }
    }

    public static ItemStack getSkull(Player player, boolean displayname) {
        return skullGetterMatcher.getSkull(player, displayname);
    }

}
