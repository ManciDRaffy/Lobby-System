package de.laudytv.lobbysystem.serverselector;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Material;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerSelectorSQLGetter {

    private final LobbySystem plugin;

    public ServerSelectorSQLGetter(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public void createItemTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS lobby_selector_items(" +
                            "id VARCHAR(16)," +
                            "server VARCHAR(16)," +
                            "display_name VARCHAR(32)," +
                            "slot TINYINT(5)," +
                            "material VARCHAR(32)," +
                            "lore TEXT," +
                            "force_glowing BOOLEAN);");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSettingTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS lobby_selector_settings(type VARCHAR(16),display_name VARCHAR(36),slot TINYINT(5),glowing BOOLEAN,material VARCHAR(32),lore TEXT,rowCount TINYINT(5),fill_empty_slots BOOLEAN,item_empty_slots VARCHAR(36));");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getIDs() {
        List<String> ids = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT id FROM lobby_selector_items");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            final int columnInt = resultSetMetaData.getColumnCount();
            while (rs.next())
                for (int i = 1; i <= columnInt; i++)
                    ids.add(rs.getString(i));
            return ids;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public String getServer(String id) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT server FROM lobby_selector_items WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("server");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDisplayName(String id) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT display_name FROM lobby_selector_items WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("display_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Integer getSlot(String id) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT slot FROM lobby_selector_items WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("slot");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Material getMaterial(String id) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT material FROM lobby_selector_items WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return Material.valueOf(rs.getString("material").toUpperCase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getLore(String id) {
        String loreRAW = null;
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT lore FROM lobby_selector_items WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                loreRAW = rs.getString("lore");
            assert loreRAW != null;
            loreRAW = loreRAW.replaceAll("\\r", "");
            return Arrays.asList(loreRAW.split("\\n"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean getForceGlowing(String id) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT force_glowing FROM lobby_selector_items WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("force_glowing");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //lobby_selector_settings

    public String getSettingsDisplayName(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT display_name FROM lobby_selector_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("display_name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getSettingsSlot(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT slot FROM lobby_selector_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("slot");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean getSettingsGlowing(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT glowing FROM lobby_selector_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("glowing");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Material getSettingsMaterial(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT material FROM lobby_selector_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return Material.valueOf(rs.getString("material").toUpperCase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getSettingsLore(String type) {
        String loreRAW = null;
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT lore FROM lobby_selector_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                loreRAW = rs.getString("lore");
            assert loreRAW != null;
            loreRAW = loreRAW.replaceAll("\\r", "");
            return Arrays.asList(loreRAW.split("\\n"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getSettingsRows(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT rowCount FROM lobby_selector_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("rowCount");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean getSettingsFillEmptySlots(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT fill_empty_slots FROM lobby_selector_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("fill_empty_slots");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Material getSettingsItemEmptySlots(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT item_in_empty_slots FROM lobby_selector_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return Material.valueOf(rs.getString("item_in_empty_slots").toUpperCase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
