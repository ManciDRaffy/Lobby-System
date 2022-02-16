package de.laudytv.lobbysystem.vanish;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class VanishManager {
    /**
     * gets the right class for the version the server is on
     */
    static IVanish iVanish;

    static {
        String packageName = VanishManager.class.getPackage().getName();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
        try {
            iVanish = (IVanish) Class.forName(packageName + "." + "Vanish" + "_" + version).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "[!!!EXTREME WARNING!!!] could not find a valid implementaton for " + packageName + "." + "Vanish" + "_" + version + ".");
        }
    }

    private final List<UUID> hiddenPlayers;
    private LobbySystem plugin;

    /**
     * Create instance of class
     *
     * @param plugin LobbySystem plugin
     */
    public VanishManager(LobbySystem plugin) {
        this.plugin = plugin;
        hiddenPlayers = new ArrayList<>();
    }

    /**
     * hides the player depending on version
     *
     * @param player player to hide/show
     * @param bool   true for hide / false for show
     */
    public void hide(Player player, boolean bool) {
        iVanish.hide(player, bool);
        if (bool)
            hiddenPlayers.add(player.getUniqueId());
        else
            hiddenPlayers.remove(player.getUniqueId());
    }

    /**
     * hides player for all online players
     */
    public void hideAll() {
        iVanish.hideAll(hiddenPlayers);
    }

    /**
     * Checks if player is hidden
     *
     * @param player player to check
     * @return is player hidden
     */
    public boolean isHidden(Player player) {
        return hiddenPlayers.contains(player.getUniqueId());
    }
}
