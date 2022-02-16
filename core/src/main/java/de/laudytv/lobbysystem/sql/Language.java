package de.laudytv.lobbysystem.sql;

import de.laudytv.lobbysystem.format.Format;
import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Language {

    private LobbySystem plugin;

    public Language(LobbySystem plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the message depending on language
     *
     * @param language to get
     * @param id       for what
     * @return message
     */
    public String getMessage(String language, String id) {
        try {
            PreparedStatement ps = plugin.mySQL.getLanguageConnection().prepareStatement("SELECT message FROM de_DE WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return Format.format(rs.getString("message"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the language of player
     *
     * @param player to get
     * @return language of player
     */
    public String getLanguage(Player player) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT language FROM playerdata WHERE uuid=?");
            ps.setString(1, player.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return Format.format(rs.getString("language"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
