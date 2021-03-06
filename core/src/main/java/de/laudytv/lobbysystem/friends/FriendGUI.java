package de.laudytv.lobbysystem.friends;


import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.friends.methods.Friend;
import de.laudytv.lobbysystem.util.GetSkull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class FriendGUI {

    private LobbySystem plugin;
    private Inventory inv;

    public FriendGUI(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public static void giveFriendItem(Player player) {
        ItemStack itemStack = GetSkull.get(player.getUniqueId().toString());
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName("┬žaFriends");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(0, itemStack);
    }

    public void openInventory(Player player) {
        inv = Bukkit.createInventory(null, 9 * 6, Friend.PREFIX + "Menu");
        HashMap<Integer, ItemStack> heads = getFriendHeads(player);
        for (int slot : heads.keySet()) {
            inv.setItem(slot, heads.get(slot));
        }
        ItemStack pane = GetSkull.getPane();
        for (int i = 36; i <= 44; i++) {
            inv.setItem(i, pane);
        }
        inv.setItem(53, GetSkull.getFriendAdd());
        player.openInventory(inv);
    }

    private HashMap<Integer, ItemStack> getFriendHeads(Player player) {
        List<String> uuidFriendsList = plugin.friendData.getFriendsList(player.getUniqueId().toString());
        List<String> online = new ArrayList<>();
        List<String> offline = new ArrayList<>();
        HashMap<Integer, ItemStack> playerHeads = new HashMap<>();
        for (String uuid : uuidFriendsList) {
            if (Bukkit.getPlayer(UUID.fromString(uuid)) != null)
                online.add(uuid);
            else
                offline.add(uuid);
        }
        Collections.sort(online);
        Collections.sort(offline);
        int slot = 0;
        for (String uuid : online) {
            ItemStack head = GetSkull.get(uuid);
            ItemMeta itemMeta = head.getItemMeta();
            assert itemMeta != null;
            String name = plugin.friendData.getName(uuid);
            itemMeta.setDisplayName("┬ža" + name);
            itemMeta.setLore(Collections.singletonList("┬žaOnline┬ž8: ┬že" + plugin.friendData.getServer(name)));
            head.setItemMeta(itemMeta);
            playerHeads.put(slot, head);
            slot++;
        }
        for (String uuid : offline) {
            ItemStack head = GetSkull.getOffline();
            ItemMeta itemMeta = head.getItemMeta();
            assert itemMeta != null;
            String name = plugin.friendData.getName(uuid);
            itemMeta.setDisplayName("┬žc" + name);
            itemMeta.setLore(Collections.singletonList("┬ž7Offline┬ž8:" + getLastOnline(name)));
            head.setItemMeta(itemMeta);
            playerHeads.put(slot, head);
            slot++;
        }
        return playerHeads;
    }

    private String getLastOnline(String name) {
        long millis = plugin.friendData.getLastOnline(name);
        long time = System.currentTimeMillis() - millis;
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        int weeks = 0;
        int months = 0;
        int years = 0;
        while (time > 1000) {
            time -= 1000;
            seconds++;
        }
        while (seconds > 60) {
            seconds -= 60;
            minutes++;
        }
        while (minutes > 60) {
            minutes -= 60;
            hours++;
        }
        while (hours > 24) {
            hours -= 24;
            days++;
        }
        while (days > 7) {
            days -= 7;
            weeks++;
        }
        while (weeks > 4.3) {
            weeks -= 4.3;
            months++;
        }
        while (months > 12) {
            months -= 12;
            years++;
        }
        String msg = "";
        if (years != 0) {
            if (years == 1)
                msg += " ┬že1 ┬ž7Year";
            else
                msg += " ┬že" + years + " ┬ž7Year";
        }
        if (months != 0) {
            if (months == 1)
                msg += " ┬že1 ┬ž7Month";
            else
                msg += " ┬že" + months + " ┬ž7Months";
        }
        if (weeks != 0) {
            if (weeks == 1)
                msg += " ┬že1 ┬ž7Week";
            else
                msg += " ┬že" + weeks + " ┬ž7Weeks";
        }
        if (days != 0) {
            if (days == 1)
                msg += " ┬že1 ┬ž7Day";
            else
                msg += " ┬že" + days + " ┬ž7Days";
        }
        if (hours != 0) {
            if (hours == 1)
                msg += " ┬že1 ┬ž7Hour";
            else
                msg += " ┬že" + hours + " ┬ž7Hours";
        }
        if (minutes != 0) {
            if (minutes == 1)
                msg += " ┬že1 ┬ž7Minute";
            else
                msg += " ┬že" + minutes + " ┬ž7Minutes";
        }
        if (seconds != 0) {
            if (seconds == 1) {
                msg += " ┬že1 ┬ž7Second";
            } else {
                msg += " ┬že" + seconds + " ┬ž7Seconds";
            }
        }
        return msg;
    }
}

