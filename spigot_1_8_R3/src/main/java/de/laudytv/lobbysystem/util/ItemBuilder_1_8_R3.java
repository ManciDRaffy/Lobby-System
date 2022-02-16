package de.laudytv.lobbysystem.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder_1_8_R3 {
    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemBuilder_1_8_R3(Material mat) {
        itemStack = new ItemStack(mat);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder_1_8_R3 setDisplayname(String s) {
        itemMeta.setDisplayName(s);
        return this;
    }


    public ItemBuilder_1_8_R3 setLore(String... s) {
        itemMeta.setLore(Arrays.asList(s));
        return this;
    }


    public ItemBuilder_1_8_R3 addItemFlags(ItemFlag... s) {
        itemMeta.addItemFlags(s);
        return this;
    }

    public ItemBuilder_1_8_R3 addEnchant(Enchantment s, int i, boolean b) {
        itemMeta.addEnchant(s, i, b);
        return this;
    }

    public ItemBuilder_1_8_R3 setGlowing() {
        itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }


    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta +
                ", itemStack=" + itemStack +
                '}';
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
