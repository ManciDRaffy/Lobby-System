package de.laudytv.lobbysystem.tablist;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.util.PluginMessage;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public class TablistManager_1_8_R3 implements TabVersionMatcher {

    private static TablistManager_1_8_R3 instance;
    private Scoreboard scoreboard;
    private HashMap<UUID, String> teams;

    public TablistManager_1_8_R3() {
        scoreboard = new Scoreboard();
        teams = new HashMap<>();
    }

    public static TablistManager_1_8_R3 getInstance() {
        if (instance == null)
            instance = new TablistManager_1_8_R3();
        return instance;
    }

    public void registerTeam(Player player, String prefix, String scolor, String suffix, int level) {
        EnumChatFormat color = EnumChatFormat.valueOf(scolor.toUpperCase());
        String s = level + player.getUniqueId().toString().substring(1, 6);
        if (scoreboard.getTeam(s) != null) {
            scoreboard.removeTeam(scoreboard.getTeam(s));
        }
        ScoreboardTeam team = scoreboard.createTeam(s);
        team.setPrefix(prefix);
        team.setSuffix(suffix);

        teams.put(player.getUniqueId(), s);
        update();
    }

    private void update() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!scoreboard.getTeam(teams.get(player.getUniqueId())).getPlayerNameSet().contains(player.getName())) {
                scoreboard.getTeam(teams.get(player.getUniqueId())).getPlayerNameSet().add(player.getName());
            }
            sendPacket(new PacketPlayOutScoreboardTeam(scoreboard.getTeam(teams.get(player.getUniqueId())), 1));
            sendPacket(new PacketPlayOutScoreboardTeam(scoreboard.getTeam(teams.get(player.getUniqueId())), 0));
        }
    }

    private void sendPacket(Packet<?> packet) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftPlayer p = (CraftPlayer) onlinePlayer;
            p.getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void setTablist(Player player) {
        LobbySystem plugin = LobbySystem.getPlugin();
        String header;
        String footer;
        if (player.hasPermission(plugin.permissions.getPermission("admin-tablist"))) {
            header = plugin.tabData.getHeader("admin");
            footer = plugin.tabData.getFooter("admin");
        } else {
            header = plugin.tabData.getHeader("default");
            footer = plugin.tabData.getFooter("default");
        }
        header = replacePlaceholders(header, player);
        footer = replacePlaceholders(footer, player);

        String finalHeader = header;
        String finalFooter = footer;
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(finalHeader))
                .write(1, WrappedChatComponent.fromText(finalFooter));
        try {
            manager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private String replacePlaceholders(String s, Player player) {
        int spawnedMobs = 0;
        for (Entity entity : player.getWorld().getEntities())
            if (entity instanceof Monster || entity instanceof Flying)
                spawnedMobs++;
        String text = s;
        text = Format.format(text);
        text = text.replaceAll("\\r", "");
        text = text.replaceAll("%player%", player.getName());
        text = text.replaceAll("%online-players%", String.valueOf(Bukkit.getOnlinePlayers().size()));
        text = text.replaceAll("%max-players%", String.valueOf(Bukkit.getMaxPlayers()));
        text = text.replaceAll("%all-players%", String.valueOf(PluginMessage.getServerCount().get("ALL")));
        text = text.replaceAll("%memory_usage%", String.valueOf((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
        text = text.replaceAll("%max_memory%", String.valueOf((Runtime.getRuntime().maxMemory() / 1048576L)));
        text = text.replaceAll("%total_memory%", String.valueOf((Runtime.getRuntime().totalMemory() / 1048576L)));
        text = text.replaceAll("%tps%", String.valueOf((int) MinecraftServer.getServer().recentTps[0]));
        text = text.replaceAll("%ping%", String.valueOf(((CraftPlayer) player).getHandle().ping));
        text = text.replaceAll("%mobs%", String.valueOf(spawnedMobs));
        text = text.replaceAll("%mob-cap%", String.valueOf(player.getWorld().getMonsterSpawnLimit()));
        return text;
    }
}
