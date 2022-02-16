package de.laudytv.lobbysystem.hideplayers;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Material;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class HidePlayersSQLGetter {

    private final LobbySystem plugin;

    public HidePlayersSQLGetter(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS hideplayers(" +
                    "type VARCHAR(32)," +
                    "display_name VARCHAR(32)," +
                    "lore TEXT," +
                    "slot TINYINT(2)," +
                    "material VARCHAR(32)" +
                    ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDisplayName(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT display_name FROM hideplayers WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("display_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getLore(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT lore FROM hideplayers WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String s = rs.getString("lore");
                s = s.replaceAll("\\r", "");
                return Arrays.asList(s.split("\\n"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Material getMaterial(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT material FROM hideplayers WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return Material.valueOf(rs.getString("material").toUpperCase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getSlot(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT slot FROM hideplayers WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("slot");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
