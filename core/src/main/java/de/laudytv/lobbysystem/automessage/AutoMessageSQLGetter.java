package de.laudytv.lobbysystem.automessage;

import de.laudytv.lobbysystem.LobbySystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutoMessageSQLGetter {
    private final LobbySystem plugin;

    public AutoMessageSQLGetter(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS auto_message(" +
                    "type VARCHAR(6)," +
                    "message TEXT," +
                    "boss_color VARCHAR(10)," +
                    "boss_type VARCHAR(12)" +
                    ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSettingsTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS auto_message_settings(" +
                    "type VARCHAR(6)," +
                    "enabled BOOLEAN," +
                    "timer TINYINT(4)" +
                    ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMessages(String type) {
        List<String> list = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT message FROM auto_message WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            final int columnInt = resultSetMetaData.getColumnCount();
            while (rs.next())
                for (int i = 1; i <= columnInt; i++)
                    list.add(rs.getString(i));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Integer getTimer(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT timer FROM auto_message_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("timer");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTimer(String type, int timer) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE auto_message_settings SET timer=? WHERE type=?");
            ps.setInt(1, timer);
            ps.setString(2, type);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean getEnabled(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT enabled FROM auto_message_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("enabled");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setEnabled(String type, boolean bool) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE auto_message_settings SET enabled=? WHERE type=?");
            ps.setBoolean(1, bool);
            ps.setString(2, type);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getColor(String message) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT boss_color FROM auto_message WHERE message=?");
            ps.setString(1, message);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("boss_color");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getType(String message) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT boss_type FROM auto_message WHERE message=?");
            ps.setString(1, message);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("boss_type");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
