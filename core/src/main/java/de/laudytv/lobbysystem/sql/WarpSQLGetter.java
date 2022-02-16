package de.laudytv.lobbysystem.sql;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WarpSQLGetter {

    public final static String PREFIX = "§3§lNAV-WARP §8| §7";
    private final LobbySystem plugin;

    public WarpSQLGetter(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public void createWarpTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS nav_warps(" +
                    "name VARCHAR(32)," +
                    "world VARCHAR(32)," +
                    "x DOUBLE," +
                    "y DOUBLE," +
                    "z DOUBLE," +
                    "yaw FLOAT," +
                    "pitch FLOAT" +
                    ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNavTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS nav_items(" +
                            "id VARCHAR(16)," +
                            "warp VARCHAR(16)," +
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
                    "CREATE TABLE IF NOT EXISTS nav_settings(type VARCHAR(16)," +
                            "display_name VARCHAR(36)," +
                            "slot TINYINT(5)," +
                            "glowing BOOLEAN," +
                            "material VARCHAR(32)," +
                            "lore TEXT,rowCount TINYINT(5)," +
                            "fill_empty_slots BOOLEAN," +
                            "item_empty_slots VARCHAR(36));");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewWarp(String name, Location location) {
        String worldName = Objects.requireNonNull(location.getWorld()).getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("INSERT IGNORE INTO nav_warps " +
                    "(name, world, x, y, z, yaw, pitch) " +
                    "VALUES " +
                    "(?,?,?,?,?,?,?);");
            ps.setString(1, name.toUpperCase());
            ps.setString(2, worldName);
            ps.setDouble(3, x);
            ps.setDouble(4, y);
            ps.setDouble(5, z);
            ps.setFloat(6, yaw);
            ps.setFloat(7, pitch);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeWarp(String warpName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("DELETE FROM nav_warps WHERE name=?");
            ps.setString(1, warpName.toUpperCase());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean isWarpSet(String warpName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT * FROM nav_warps WHERE name=?");
            ps.setString(1, warpName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public String getWorld(String warpName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT world FROM nav_warps WHERE name=?");
            ps.setString(1, warpName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("world");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getX(String warpName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT x FROM nav_warps WHERE name=?");
            ps.setString(1, warpName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getDouble("x");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getY(String warpName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT y FROM nav_warps WHERE name=?");
            ps.setString(1, warpName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getDouble("y");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getZ(String warpName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT z FROM nav_warps WHERE name=?");
            ps.setString(1, warpName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getDouble("z");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Float getYaw(String warpName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT yaw FROM nav_warps WHERE name=?");
            ps.setString(1, warpName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getFloat("yaw");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Float getPitch(String warpName) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT pitch FROM nav_warps WHERE name=?");
            ps.setString(1, warpName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getFloat("pitch");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Location getLocation(String warpName) {
        World world = Bukkit.getWorld(getWorld(warpName.toUpperCase()));
        double x = getX(warpName.toUpperCase());
        double y = getY(warpName.toUpperCase());
        double z = getZ(warpName.toUpperCase());
        float yaw = getYaw(warpName.toUpperCase());
        float pitch = getPitch(warpName.toUpperCase());
        return new Location(world, x, y, z, yaw, pitch);
    }

    public List<String> getIDs() {
        List<String> ids = new ArrayList<>();
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT id FROM nav_items");
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

    public String getWarp(String id) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT warp FROM nav_items WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("warp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDisplayName(String id) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT display_name FROM nav_items WHERE id=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT slot FROM nav_items WHERE id=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT material FROM nav_items WHERE id=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT lore FROM nav_items WHERE id=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT force_glowing FROM nav_items WHERE id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("force_glowing");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //nav_settings

    public String getSettingsDisplayName(String type) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT display_name FROM nav_settings WHERE type=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT slot FROM nav_settings WHERE type=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT glowing FROM nav_settings WHERE type=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT material FROM nav_settings WHERE type=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT lore FROM nav_settings WHERE type=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT rowCount FROM nav_settings WHERE type=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT fill_empty_slots FROM nav_settings WHERE type=?");
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
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT item_in_empty_slots FROM nav_settings WHERE type=?");
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return Material.valueOf(rs.getString("item_in_empty_slots").toUpperCase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getWarpBySlot(int slot) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT warp FROM nav_items WHERE slot=?");
            ps.setInt(1, slot);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("warp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
