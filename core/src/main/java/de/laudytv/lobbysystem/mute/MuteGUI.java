package de.laudytv.lobbysystem.mute;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class MuteGUI {

    private final LobbySystem plugin = LobbySystem.getPlugin();
    private Inventory inv;
    private Player player;
    private int rows = 1;
    private String title = "";

    public MuteGUI(Player player) {
        this.player = player;
        inv = Bukkit.createInventory(null, 9 * rows, title);
    }


}
