package de.laudytv.lobbysystem.mute;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MuteSQL {


    private final LobbySystem plugin;

    public MuteSQL(LobbySystem plugin) {
        this.plugin = plugin;
    }


    public void createTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS mutes_presets(" +
                    "slot TINYINT(3)," +
                    "item TEXT," +
                    "footer TEXT" +
                    ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mutePlayer(String name, Player player, long time, String reason) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("INSERT IGNORE INTO mutes (uuid, name, start, until, reason, from_uuid, from_name)" +
                    "VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, plugin.playerData.getUUIDByName(name));
            ps.setString(2, name);
            ps.setLong(3, System.currentTimeMillis());
            ps.setLong(4, time);
            ps.setString(5, reason);
            ps.setString(6, player.getUniqueId().toString());
            ps.setString(7, player.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unMutePlayer(String uuid) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("DELETE FROM mutes WHERE uuid=?");
            ps.setString(1, uuid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean isPlayerMuted(String name) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT * FROM mutes WHERE uuid=?");
            ps.setString(1, plugin.playerData.getUUIDByName(name));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getPlayersTimeMuted() {
        List<String> playersTimeMuted = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT uuid FROM mutes");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            final int columnInt = resultSetMetaData.getColumnCount();
            while (rs.next())
                for (int i = 1; i <= columnInt; i++)
                    playersTimeMuted.add(rs.getString(i));
            return playersTimeMuted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playersTimeMuted;
    }

    public long getUntilTimeByUUID(String mutedPlayerUUID) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT until FROM mutes WHERE uuid=?");
            ps.setString(1, mutedPlayerUUID);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getLong("until");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public void checkMutes() {
        List<String> uuids = getPlayersTimeMuted();
        for (String uuid : uuids) {
            long until = getUntilTimeByUUID(uuid);
            long milli = System.currentTimeMillis();
            if (milli > until) {
                String name = plugin.playerData.getNameByUUID(uuid);
                if (isPlayerMuted(name)) {
                    unMutePlayer(uuid);
                }
            }
        }
    }
}

