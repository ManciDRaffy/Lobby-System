package de.laudytv.lobbysystem.sql;

import de.laudytv.lobbysystem.LobbySystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionsSQLGetter {

    private final LobbySystem plugin;

    public PermissionsSQLGetter(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS permissions(" +
                    "name VARCHAR(32)," +
                    "permission VARCHAR(32)," +
                    "message TEXT" +
                    ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPermission(String name) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT permission FROM permissions WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("permission");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPermissionMessage() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT message FROM permissions");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("permission");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
