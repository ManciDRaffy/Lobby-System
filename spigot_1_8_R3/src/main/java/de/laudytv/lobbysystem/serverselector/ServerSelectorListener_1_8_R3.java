package de.laudytv.lobbysystem.serverselector;

import de.laudytv.lobbysystem.util.ItemBuilder_1_8_R3;
import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.build.BuildManager;
import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.util.PluginMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ServerSelectorListener_1_8_R3 implements Listener, ServerSelectorMatcher {
    private static final LobbySystem plugin = LobbySystem.getPlugin();
    private final PluginMessage pluginMessage = new PluginMessage();

    @EventHandler
    public void onServerSelector(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().hasItemMeta()) {
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (e.getItem().getItemMeta().getDisplayName().equals(Format.format(plugin.ssData.getSettingsDisplayName("item")))
                        && e.getItem().getType().equals(plugin.ssData.getSettingsMaterial("item"))) {
                    e.setCancelled(true);
                    ServerSelectorGUI_1_8_R3 gui = new ServerSelectorGUI_1_8_R3(e.getPlayer());
                    gui.openInventory();
                }
            }
        }
    }

    public void giveServerSelectorItem(Player p) {
        ItemStack itemStack;
        int slot = plugin.ssData.getSettingsSlot("item");
        String displayName = plugin.ssData.getSettingsDisplayName("item");
        Material item = plugin.ssData.getSettingsMaterial("item");
        displayName = Format.format(displayName);
        List<String> loreList = plugin.ssData.getSettingsLore("item");
        for (int i = 0; i < loreList.size(); i++) {
            loreList.set(i, Format.format(loreList.get(i)));
        }
        if (plugin.ssData.getSettingsGlowing("item"))
            itemStack = new ItemBuilder_1_8_R3(item).setDisplayname(displayName).setLore(loreList.toArray(new String[0])).setGlowing().build();
        else
            itemStack = new ItemBuilder_1_8_R3(item).setDisplayname(displayName).setLore(loreList.toArray(new String[0])).build();

        p.getInventory().setItem(slot, itemStack);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (e.getView().getTitle().equals(Format.format(plugin.ssData.getSettingsDisplayName("inventory")))) {
                e.setCancelled(true);
                Player player = (Player) e.getWhoClicked();
                List<String> ids = plugin.ssData.getIDs();
                for (String id : ids) {
                    if (plugin.ssData.getSlot(id) == e.getSlot()) {
                        String server = plugin.ssData.getServer(id);
                        if (PluginMessage.serverCount.containsKey(server))
                            pluginMessage.connect(player, server);
                        else
                            return;
                    }
                }
            }
            if (!BuildManager.isBuilding(p))
                e.setCancelled(true);
        }
    }
}
