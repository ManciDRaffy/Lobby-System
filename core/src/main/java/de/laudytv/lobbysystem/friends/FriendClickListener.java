package de.laudytv.lobbysystem.friends;

import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.friends.methods.Friend;
import de.laudytv.lobbysystem.util.SignGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;

public class FriendClickListener implements Listener {


    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() != null
                && event.getItem().hasItemMeta()
                && Objects.requireNonNull(event.getItem().getItemMeta()).hasDisplayName()
                && event.getItem().getItemMeta().getDisplayName().equals("§aFriends")) {
            FriendGUI gui = new FriendGUI(LobbySystem.getPlugin());
            gui.openInventory(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(Friend.PREFIX + "Menu")) return;
        event.setCancelled(true);
        if (!(event.getCurrentItem() != null && Objects.requireNonNull(event.getCurrentItem()).getType() != Material.AIR))
            return;
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        String itemName = Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
        if (itemName.equals("§aAdd Friend")) {
            openSignGUI(player);
        }
    }

    private void openSignGUI(Player toPlayer) {
        LobbySystem plugin = LobbySystem.getPlugin();
        SignGUI signGUI = new SignGUI(plugin);
        SignGUI.Menu menu = signGUI.newMenu(Arrays.asList("", "§atype the name ", "§aof player you", " §awant to add"))
                .reopenIfFail(false)
                .response((player, string) -> {
                    Friend.add(player, string[0]);
                    return true;
                });
        menu.open(toPlayer);
    }
}

