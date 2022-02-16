package de.laudytv.lobbysystem.navigator;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.util.ItemBuilder_1_16_R3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Navigator_1_16_R3 implements NavigatorMatcher {

    private final LobbySystem plugin = LobbySystem.getPlugin();

    public void initializeItems(Inventory inventory, List<String> ids) {
        for (String id : ids) {
            ItemStack itemStack;
            Material material = plugin.warpData.getMaterial(id);
            String displayName = plugin.warpData.getDisplayName(id);
            List<String> loreList = plugin.warpData.getLore(id);
            for (int i = 0; i < loreList.size(); i++)
                loreList.set(i, Format.format(loreList.get(i)));
            String[] lore = loreList.toArray(new String[0]);
            String warp = plugin.warpData.getWarp(id);

            displayName = Format.format(displayName);

            int slot = plugin.warpData.getSlot(id);
            if (plugin.warpData.getForceGlowing(id))
                itemStack = new ItemBuilder_1_16_R3(material)
                        .setDisplayname(displayName)
                        .setLore(lore)
                        .setGlowing()
                        .build();
            else
                itemStack = new ItemBuilder_1_16_R3(material)
                        .setDisplayname(displayName)
                        .setLore(lore)
                        .build();
            inventory.setItem(slot, itemStack);
        }
    }

    public void getNavigatorItem(Player player) {
        String displayName = plugin.warpData.getSettingsDisplayName("item");
        List<String> loreList = plugin.warpData.getSettingsLore("item");
        for (int i = 0; i < loreList.size(); i++)
            loreList.set(i, Format.format(loreList.get(i)));
        String[] lore = loreList.toArray(new String[0]);
        int slot = plugin.warpData.getSettingsSlot("item");
        displayName = Format.format(displayName);
        ItemStack itemStack;
        if (plugin.warpData.getSettingsGlowing("item"))
            itemStack = new ItemBuilder_1_16_R3(plugin.warpData.getSettingsMaterial("item"))
                    .setDisplayname(displayName)
                    .setLore(lore).setGlowing().build();
        else
            itemStack = new ItemBuilder_1_16_R3(plugin.warpData.getSettingsMaterial("item"))
                    .setDisplayname(displayName)
                    .setLore(lore).build();
        player.getInventory().setItem(slot, itemStack);
    }
}
