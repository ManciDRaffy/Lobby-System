package de.laudytv.lobbysystem.tablist;

import de.laudytv.lobbysystem.LobbySystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TablistSQLGetter {

    private final LobbySystem plugin;

    public TablistSQLGetter(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS tablist(" +
                    "type VARCHAR(16)," +
                    "header TEXT," +
                    "footer TEXT" +
                    ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getHeader(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT header FROM tablist WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("header");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFooter(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT footer FROM tablist WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("footer");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
