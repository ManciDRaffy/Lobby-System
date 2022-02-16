package de.laudytv.lobbysystem.util;

import de.laudytv.lobbysystem.LobbySystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PlayerDataSQL {
    private final LobbySystem plugin;

    public PlayerDataSQL(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public Boolean existsPlayer(String uuid) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT * FROM playerdata WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean existsPlayerByName(String name) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT * FROM playerdata WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getIP(String playerName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT ip FROM playerdata WHERE name=?");
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("ip");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNameByUUID(String uuid) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT name FROM playerdata WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUUIDByName(String playerName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT uuid FROM playerdata WHERE name=?");
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("uuid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getFirstJoin(String playerName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT firstJoin FROM playerdata WHERE name=?");
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return Date.from(rs.getTimestamp("firstJoin").toInstant());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setRank(String playerName, String rank) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE rank=? FROM playerdata WHERE name=?");
            ps.setString(1, rank);
            ps.setString(2, playerName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getRank(String playerName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT rank FROM playerdata WHERE name=?");
            ps.setString(1, playerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("rank");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
