package de.laudytv.lobbysystem.util;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    /**
     * instance of ItemBuilder
     *
     * @param materialName name of material
     */
    public ItemBuilder(String materialName) {
        itemStack = XMaterial.valueOf(materialName.toUpperCase()).parseItem();
        assert itemStack != null;
        itemMeta = itemStack.getItemMeta();
    }

    /**
     * sets the display name
     *
     * @param displayName display name to set
     * @return this
     */
    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        return this;
    }

    /**
     * makes the item unbreakable
     *
     * @param bool unbreakable?
     * @return this
     */
    public ItemBuilder setUnbreakable(boolean bool) {
        itemMeta.setUnbreakable(true);
        return this;
    }

    /**
     * sets the lore of item
     *
     * @param lore text in lore
     * @return this
     */
    public ItemBuilder setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * adds an enchantment to the item
     *
     * @param enchantment enchantment
     * @param level       level of enchant
     * @return this
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * adds an NBT Tag to the item
     *
     * @param itemFlags nbt tag to set
     * @return this
     */
    public ItemBuilder addItemFlag(ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    /**
     * sets the item glowing
     *
     * @param bool glowing?
     * @return this
     */
    public ItemBuilder setGlowing(boolean bool) {
        itemMeta.addEnchant(Enchantment.LUCK, 100, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * @return item stack in string
     */
    @Override
    public String toString() {
        return "ItemBuilder{" +
                "itemMeta=" + itemMeta +
                ", itemStack=" + itemStack +
                '}';
    }

    /**
     * builds the item
     *
     * @return item stack
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
