package de.laudytv.lobbysystem.friends.methods;

import de.laudytv.lobbysystem.LobbySystem;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class Friend {

    public static final String PREFIX = "§a§lFRIENDS §8| §7";

    private static final LobbySystem plugin = LobbySystem.getPlugin();

    public static void add(Player player, String target) {
        if (!plugin.friendData.existsPlayer(target)) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "Player has never visited the server"));
            return;
        }
        if (Objects.equals(player.getName(), target)) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "You cannot be friends with yourself"));
            return;
        }
        List<String> friendList = plugin.friendData.getFriendsList(player.getUniqueId().toString());
        String targetUUID = plugin.friendData.getUUID(target);
        if (friendList.contains(targetUUID)) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "You are already friends with the player"));
            return;
        }
        if (plugin.friendData.getRequests(targetUUID).contains(player.getUniqueId().toString())) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "You've already sent that player a request"));
            return;
        }
        if (plugin.friendData.getRequests(player.getUniqueId().toString()).contains(targetUUID)) {
            TextComponent accept = new TextComponent("§2✔");
            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + target));
            TextComponent between = new TextComponent(" §8| ");
            TextComponent deny = new TextComponent("§4✘");
            deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + target));
            TextComponent msg = new TextComponent(PREFIX + "That player already sent you a request, click here to accept ");
            msg.addExtra(accept);
            msg.addExtra(between);
            msg.addExtra(deny);
            player.spigot().sendMessage(msg);
            return;
        }
        if (!plugin.friendData.getAllowRequest(targetUUID)) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "That player doesn't want friend requests"));
            return;
        }
        plugin.friendData.addRequest(targetUUID, player.getUniqueId().toString());
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            TextComponent accept = new TextComponent("§2✔");
            accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + player.getName()));
            TextComponent between = new TextComponent(" §8| ");
            TextComponent deny = new TextComponent("§4✘");
            deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + player.getName()));
            TextComponent msg = new TextComponent(PREFIX + "§a" + player.getName() + " §7has sent you an friend request, click here to accept ");
            msg.addExtra(accept);
            msg.addExtra(between);
            msg.addExtra(deny);
            targetPlayer.spigot().sendMessage(msg);
        }
        player.spigot().sendMessage(new TextComponent(PREFIX + "You've sent §a" + target + " §7a friend request"));
    }
   /*
    public static void remove(Player player, String target) {
        List<String> friendList = plugin.data.getFriendsList(player.getUniqueId().toString());
        String targetUUID = plugin.data.getUUID(target);
        if (plugin.data.getRequests(targetUUID).contains(player.getUniqueId().toString())) {
            plugin.data.removeRequest(targetUUID, player.getUniqueId().toString());
            player.spigot().sendMessage(new TextComponent(PREFIX + "You removed the request for this player"));
            return;
        }
        if (!friendList.contains(targetUUID)) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "You are not friends with this player"));
            return;
        }
        plugin.data.removeFriend(targetUUID, player.getUniqueId().toString());
        plugin.data.removeFriend(player.getUniqueId().toString(), targetUUID);
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            targetPlayer.spigot().sendMessage(new TextComponent(PREFIX + "§a" + player.getName() + " §7removed you from his friends"));
        }
        player.spigot().sendMessage(new TextComponent(PREFIX + "You've removed §a" + target + " §7from your friends"));
    }

    public static void accept(Player player, String target) {
        String targetUUID = plugin.data.getUUID(target);
        List<String> requestList = plugin.data.getRequestsList(player.getUniqueId().toString());
        if (!requestList.contains(targetUUID)) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "That player hasn't sent you a friend request"));
            return;
        }
        plugin.data.removeRequest(player.getUniqueId().toString(), targetUUID);
        plugin.data.addFriend(targetUUID, player.getUniqueId().toString());
        plugin.data.addFriend(player.getUniqueId().toString(), targetUUID);
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            targetPlayer.spigot().sendMessage(new TextComponent(PREFIX + "§a" + player.getName() + " §7accepted your friend request"));
        }
        player.spigot().sendMessage(new TextComponent(PREFIX + "You've accepted §a" + target + "§7's friend request"));
    }

    public static void acceptAll(Player player) {
        if (plugin.data.getRequestsCount(player.getUniqueId().toString()) == 0) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "You have no requests to accept"));
            return;
        }
        List<String> requestList = plugin.data.getRequestsList(player.getUniqueId().toString());
        for (String uuid : requestList) {
            plugin.data.removeRequest(player.getUniqueId().toString(), uuid);
            plugin.data.addFriend(player.getUniqueId().toString(), uuid);
            plugin.data.addFriend(uuid, player.getUniqueId().toString());
            player.spigot().sendMessage(new TextComponent(PREFIX + "You have accepted §a" + plugin.data.getName(uuid) + "§7's friend request"));
            Player newFriend = Bukkit.getPlayer(UUID.fromString(uuid));
            if (newFriend != null)
                newFriend.spigot().sendMessage(new TextComponent(PREFIX + "§a" + player.getName() + " §7accepted your friend request"));
        }
    }

    public static void deny(Player player, String target) {
        String targetUUID = plugin.data.getUUID(target);
        List<String> requestList = plugin.data.getRequestsList(player.getUniqueId().toString());
        if (!requestList.contains(targetUUID)) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "That player hasn't sent you a friend request"));
            return;
        }
        plugin.data.removeRequest(player.getUniqueId().toString(), targetUUID);
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            targetPlayer.spigot().sendMessage(new TextComponent(PREFIX + "§a" + player.getName() + " §7denied your friend request"));
        }
        player.spigot().sendMessage(new TextComponent(PREFIX + "You've denied §a" + target + "§7's friend request"));
    }

    public static void denyAll(Player player) {
        if (plugin.data.getRequestsCount(player.getUniqueId().toString()) == 0) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "You have no requests to deny"));
            return;
        }
        List<String> requestList = plugin.data.getRequestsList(player.getUniqueId().toString());
        for (String uuid : requestList) {
            plugin.data.removeRequest(player.getUniqueId().toString(), uuid);
            player.spigot().sendMessage(new TextComponent(PREFIX + "You have denied §c" + plugin.data.getName(uuid) + "§7's friend request"));
            Player notAFriend = Bukkit.getPlayer(UUID.fromString(uuid));
            if (notAFriend != null)
                notAFriend.spigot().sendMessage(new TextComponent(PREFIX + "§c" + player.getName() + " §7denied your friend request"));
        }
    }

    public static void clear(Player player) {
        if (plugin.data.getFriendsCount(player.getUniqueId().toString()) == 0) {
            player.spigot().sendMessage(new TextComponent(PREFIX + "§cYou have no friends to remove"));
            return;
        }
        for (String uuid : plugin.data.getFriendsList(player.getUniqueId().toString())) {
            plugin.data.removeFriend(player.getUniqueId().toString(), uuid);
            plugin.data.removeFriend(uuid, player.getUniqueId().toString());
            Player target = Bukkit.getPlayer(UUID.fromString(uuid));
            if (target != null)
                target.spigot().sendMessage(new TextComponent(PREFIX + "§a" + player.getName() + " §7removed you from his friends"));
        }
        player.spigot().sendMessage(new TextComponent(PREFIX + "You have removed all your friends"));
    }
    */
}