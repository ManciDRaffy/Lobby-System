package de.laudytv.lobbysystem.nick;

import com.mojang.authlib.GameProfile;
import de.laudytv.lobbysystem.LobbySystem;
import de.laudytv.lobbysystem.util.GameProfileBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SkinSQL {

    // This class saves the skins in database cache, so the next time the plugin is loaded it gets loaded again

    private static HashMap<UUID, GameProfile> cache = new HashMap<>();
    private final LobbySystem plugin;

    public SkinSQL(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public void createItemTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS skin_cache(" +
                            "uuid VARCHAR(36)," +
                            "gameprofile TEXT" +
                            ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UUID> getUUIDs() {
        List<UUID> UUIDs = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT uuid FROM skin_cache");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            final int columnInt = resultSetMetaData.getColumnCount();
            while (rs.next())
                for (int i = 1; i <= columnInt; i++)
                    UUIDs.add(UUID.fromString(rs.getString(i)));
            return UUIDs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GameProfile> getGameProfiles() {
        List<GameProfile> gameProfiles = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT uuid FROM skin_cache");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            final int columnInt = resultSetMetaData.getColumnCount();
            while (rs.next())
                for (int i = 1; i <= columnInt; i++)
                    gameProfiles.add(GameProfileBuilder.gson.fromJson(rs.getString(i), GameProfile.class));
            return gameProfiles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
