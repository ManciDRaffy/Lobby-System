package de.laudytv.lobbysystem.nametag;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class NameTagManager {

    static NameTagMatcher nameTagMatcher;

    static {
        String packagename = NameTagManager.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            nameTagMatcher = (NameTagMatcher) Class.forName(packagename + "." + "NameTag" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "SaltyUtil could not find a valid implementation for \"" + packagename + "." + "NameTag" + "_" + version + "\".");
        }
    }

    private LobbySystem plugin;
    private ProtocolManager manager;

    public NameTagManager(LobbySystem plugin) {
        this.plugin = plugin;
        this.manager = ProtocolLibrary.getProtocolManager();
    }

    public void setNameTag(Player player, String nametag) {
        nameTagMatcher.setName(player, nametag);
    }
}
