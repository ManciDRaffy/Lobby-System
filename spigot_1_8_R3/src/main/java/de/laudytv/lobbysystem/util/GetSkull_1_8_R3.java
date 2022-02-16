package de.laudytv.lobbysystem.util;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.util.GetSkullMatcher;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class GetSkull_1_8_R3 implements GetSkullMatcher {

    public ItemStack get(String uuid, LobbySystem plugin) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(plugin.friendData.getName(uuid));
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    public ItemStack getOffline() {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal());
    }

    public ItemStack getPane() {
        ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        itemStack.setDurability((short) 15);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("§c");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getFriendAdd() {
        ItemStack itemStack = new ItemStack(Material.SIGN);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§aAdd Friend");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Material getSign() {
        return Material.SIGN;
    }

}
