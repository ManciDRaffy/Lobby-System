package de.laudytv.lobbysystem.nick;

import com.mojang.authlib.GameProfile;
import de.laudytv.lobbysystem.util.GameProfileBuilder;
import de.laudytv.lobbysystem.util.UUIDFetcher;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class Nick_1_8_R3 implements NickMatcher {

    private void sendPacket(Packet<?> packet) {
        Bukkit.getOnlinePlayers().forEach(player -> ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet));
    }

    private void sendPacket(Packet<?> packet, Player target) {
        for (Player player : Bukkit.getOnlinePlayers())
            if (!player.equals(target))
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void setNick(Player player, String nick) {
        try {
            CraftPlayer craftPlayer = (CraftPlayer) player;
            Class profileClass = craftPlayer.getProfile().getClass();
            GameProfile gameProfile = GameProfileBuilder.fetch(UUIDFetcher.getUUID(nick));

            assert gameProfile != null;

            // change the name
            Field namePlayer = profileClass.getDeclaredField("name");
            namePlayer.setAccessible(true);
            namePlayer.set(((CraftPlayer) player).getProfile(), gameProfile.getName());
            namePlayer.setAccessible(false);
            // change the skin
            Field properties = profileClass.getDeclaredField("properties");
            properties.setAccessible(true);
            properties.set(((CraftPlayer) player).getProfile(), gameProfile.getProperties());
            properties.setAccessible(false);

            // send the player list remove player packet
            PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer.getHandle());
            sendPacket(packetPlayOutPlayerInfo);
            // send the player list add player
            PacketPlayOutPlayerInfo packetPlayOutPlayerInfo1 = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, craftPlayer.getHandle());
            sendPacket(packetPlayOutPlayerInfo1);
            // send the entity destroy to everyone except player
            PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(craftPlayer.getEntityId());
            sendPacket(packetPlayOutEntityDestroy, player);
            // send the entity spawn to everyone except player
            PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn(craftPlayer.getHandle());
            sendPacket(packetPlayOutNamedEntitySpawn, player);
            // update player
            craftPlayer.getHandle().server.getPlayerList().moveToWorld(craftPlayer.getHandle(), 0, false, craftPlayer.getLocation(), true);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
