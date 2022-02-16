package de.laudytv.lobbysystem.util;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class GetSkull_1_16_R3 implements GetSkullMatcher {

    public ItemStack get(String uuid, LobbySystem plugin) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        assert skullMeta != null;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }

    public ItemStack getOffline() {
        return new ItemStack(Material.WITHER_SKELETON_SKULL);
    }

    public ItemStack getPane() {
        ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("§c");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getFriendAdd() {
        ItemStack itemStack = new ItemStack(Material.SPRUCE_SIGN);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("§aAdd Friend");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public Material getSign() {
        return Material.SPRUCE_SIGN;
    }
}
