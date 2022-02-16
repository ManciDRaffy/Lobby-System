package de.laudytv.lobbysystem.navigator;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public interface NavigatorMatcher {

    void initializeItems(Inventory inventory, List<String> ids);

    void getNavigatorItem(Player player);
}
