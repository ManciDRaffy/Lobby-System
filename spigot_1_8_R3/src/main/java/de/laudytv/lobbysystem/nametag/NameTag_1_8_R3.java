package de.laudytv.lobbysystem.nametag;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class NameTag_1_8_R3 implements NameTagMatcher{

    public void setName(Player player, String name) {
        try {
            CraftPlayer craftPlayer = ((CraftPlayer) player);
            EntityPlayer entityHuman = craftPlayer.getHandle();
            Field bH = entityHuman.getClass().getDeclaredField("bH");
            bH.setAccessible(true);
            bH.set(craftPlayer, new GameProfile(player.getUniqueId(), name));
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.hidePlayer(player);
                onlinePlayer.showPlayer(player);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
