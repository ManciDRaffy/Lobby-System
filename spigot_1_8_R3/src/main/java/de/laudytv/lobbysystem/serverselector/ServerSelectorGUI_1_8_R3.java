package de.laudytv.lobbysystem.serverselector;

import de.laudytv.lobbysystem.util.ItemBuilder_1_8_R3;
import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.util.PluginMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ServerSelectorGUI_1_8_R3 {

    private static final LobbySystem plugin = LobbySystem.getPlugin();
    private Inventory inv;
    private HashMap<Integer, ItemStack> invItems = new HashMap<>();
    private Player viewer;

    public ServerSelectorGUI_1_8_R3(Player viewer) {
        int rows = plugin.ssData.getSettingsRows("inventory");
        String title = plugin.ssData.getSettingsDisplayName("inventory");
        title = Format.format(title);
        this.viewer = viewer;
        this.inv = Bukkit.getServer().createInventory(null, rows * 9, title);
        initializeItems();
    }

    public void initializeItems() {
        List<String> ids = plugin.ssData.getIDs();
        for (String id : ids) {
            int slot = plugin.ssData.getSlot(id);
            String displayName = plugin.ssData.getDisplayName(id);
            displayName = Format.format(displayName);
            Material material = plugin.ssData.getMaterial(id);
            List<String> loreList = plugin.ssData.getLore(id);
            String server = plugin.ssData.getServer(id);
            ItemStack itemStack;

            for (int i = 0; i < loreList.size(); i++) {
                loreList.set(i, Format.format(loreList.get(i)));
                loreList.set(i, loreList.get(i).replaceAll("%online-players%", String.valueOf(PluginMessage.serverCount.get(server))));
                loreList.set(i, loreList.get(i).replaceAll("%all-players%", String.valueOf(PluginMessage.getServerCount().get("ALL"))));
                loreList.set(i, loreList.get(i).replaceAll("%player%", viewer.getName()));
            }
            String serverOfPlayer = plugin.getPluginMessage().getServer(viewer);
            if (plugin.ssData.getForceGlowing(id) || Objects.equals(server, serverOfPlayer))
                itemStack = new ItemBuilder_1_8_R3(material).setDisplayname(displayName).setLore(loreList.toArray(new String[0])).setGlowing().build();
            else
                itemStack = new ItemBuilder_1_8_R3(material).setDisplayname(displayName).setLore(loreList.toArray(new String[0])).build();
            invItems.put(slot, itemStack);
        }
        for (int slot : invItems.keySet())
            inv.setItem(slot, invItems.get(slot));
    }

    public void openInventory() {
        viewer.openInventory(inv);
    }

}
