package de.laudytv.lobbysystem.util;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;

public class PluginMessage implements PluginMessageListener {
    public static HashMap<String, Integer> serverCount = new HashMap<>();
    public static String[] serverList = new String[0];
    private String serverOfPlayer;

    public static HashMap<String, Integer> getServerCount() {
        return serverCount;
    }

    public static String[] getServerList() {
        return serverList;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subchannel = input.readUTF();
        if (subchannel.equals("PlayerCount")) {
            String server = input.readUTF();
            int playerCount = input.readInt();
            serverCount.remove(server);
            serverCount.put(server, playerCount);
        } else if (subchannel.equals("GetServers")) {
            serverList = input.readUTF().split(", ");
        } else if (subchannel.equals("GetServer")) {
            serverOfPlayer = input.readUTF();
        }
    }

    public void connect(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);
        player.sendPluginMessage(LobbySystem.getPlugin(), "BungeeCord", output.toByteArray());
    }

    public void saveAllPlayersOnServer(String server) {
        if (Bukkit.getOnlinePlayers().size() == 0) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        assert player != null;
        player.sendPluginMessage(LobbySystem.getPlugin(), "BungeeCord", out.toByteArray());
    }

    public void saveAllServers() {
        if (Bukkit.getOnlinePlayers().size() == 0) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        out.writeUTF("GetServers");
        assert player != null;
        player.sendPluginMessage(LobbySystem.getPlugin(), "BungeeCord", out.toByteArray());
    }

    public void saveAllPlayers() {
        for (String server : serverList)
            saveAllPlayersOnServer(server);
        saveAllPlayersOnServer("ALL");
    }

    public String getServer(Player player) {
        serverOfPlayer = "";
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        player.sendPluginMessage(LobbySystem.getPlugin(), "BungeeCord", out.toByteArray());
        return serverOfPlayer;
    }
}
