package de.laudytv.lobbysystem.bossbar;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.laudytv.lobbysystem.format.Format;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.UUID;

public class BossBar_1_16_R3 implements BossBarMatcher {

    private static HashMap<String, Integer> colors = new HashMap<>();
    private static HashMap<String, Integer> divisions = new HashMap<>();

    private static void initHashMaps() {
        colors.put("PINK", 0);
        colors.put("BLUE", 1);
        colors.put("RED", 2);
        colors.put("GREEN", 3);
        colors.put("YELLOW", 4);
        colors.put("PURPLE", 5);
        colors.put("WHITE", 6);
        divisions.put("blank", 0);
        divisions.put("6", 1);
        divisions.put("10", 2);
        divisions.put("12", 3);
        divisions.put("16", 4);
    }

    public void spawnBar(Player player, String text, String color, String division) {
        initHashMaps();
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = manager.createPacket(PacketType.Play.Server.BOSS);
        packet.getUUIDs().write(0, UUID.randomUUID());
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(Format.format(text)));
        packet.getFloat().write(0, 1F);
        packet.getIntegers().write(1, colors.get(color.toUpperCase()));
        packet.getIntegers().write(2, divisions.get(division));
        try {
            manager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Player player) {

    }

    @Override
    public void teleport(Player player) {

    }

    @Override
    public void rename(Player player, String rename) {

    }
}
