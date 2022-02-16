package de.laudytv.lobbysystem.bossbar;

import de.laudytv.lobbysystem.format.Format;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BossBar_1_8_R3 implements BossBarMatcher {

    private static final HashMap<String, Entity> entitiesForPlayer = new HashMap<>();
    private static EntityEnderDragon dragon;

    @Override
    public void spawnBar(Player player, String text, String color, String division) {
        if (entitiesForPlayer.containsKey(player.getName())) {
            rename(player, text);
            return;
        }
        Location loc = player.getLocation();
        loc.add(loc.getDirection().multiply(50));
        WorldServer world = ((CraftWorld) player.getLocation().getWorld()).getHandle();

        //EntityEnderDragon wither = new EntityEnderDragon(world);
        EntityWither wither = new EntityWither(world);
        wither.setInvisible(true);
        wither.setCustomName(Format.format(text));
        while (!loc.getBlock().getType().equals(Material.AIR))
            loc.setY(loc.getY() + 1);
        loc.setY(loc.getY() + 4);
        wither.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        NBTTagCompound tag = new NBTTagCompound();
        wither.b(true);
        wither.k(true);
        entitiesForPlayer.put(player.getName(), wither);

        Packet<?> packet = new PacketPlayOutSpawnEntityLiving(wither);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void remove(Player player) {
        if (!entitiesForPlayer.containsKey(player.getName())) return;
        Packet<?> packet = new PacketPlayOutEntityDestroy(entitiesForPlayer.get(player.getName()).getId());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        entitiesForPlayer.remove(player.getName());
    }

    @Override
    public void rename(Player player, String rename) {
        if (!entitiesForPlayer.containsKey(player.getName())) return;
        remove(player);
        spawnBar(player, rename, null, null);
    }

    @Override
    public void teleport(Player player) {
        if (!entitiesForPlayer.containsKey(player.getName())) return;
        Location loc = player.getLocation();
        Entity wither = entitiesForPlayer.get(player.getName());
        loc.add(loc.getDirection().multiply(50));
        while (!loc.getBlock().getType().equals(Material.AIR))
            loc.setY(loc.getY() + 1);
        loc.setY(loc.getY() + 4);
        wither.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        Packet<?> packet = new PacketPlayOutEntityTeleport(entitiesForPlayer.get(player.getName()));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
