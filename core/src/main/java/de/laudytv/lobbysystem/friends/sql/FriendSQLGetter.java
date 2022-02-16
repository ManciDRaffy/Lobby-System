package de.laudytv.lobbysystem.friends.sql;

import de.laudytv.lobbysystem.LobbySystem;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendSQLGetter {

    private final LobbySystem plugin;

    public FriendSQLGetter(LobbySystem plugin) {
        this.plugin = plugin;
    }

    public void createTable() {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS friends(" +
                    "name VARCHAR(16)," +               // NAME DES SPIELERS
                    "uuid VARCHAR(36)," +               // UUID DES SPIELERS
                    "online BOOLEAN," +
                    "friends TEXT," +                   // FREUNDE DES SPIELERS
                    "requests TEXT," +                  // ANFRAGEN DES SPIELERS
                    "allowRequest BOOLEAN," +        // ERLAUBE ANFRANGE (true/false)
                    "allowJump BOOLEAN," +           // ERLAUBE SPRINGEN (true/false)
                    "allowNotify BOOLEAN," +         // ERLAUBE NACHRICHTEN (true/false)
                    "lastOnline BIGINT," +           // ZULETZT ONLINE
                    "server VARCHAR(50)" +              // NAME DES SERVERS
                    ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(Player player) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("INSERT IGNORE INTO friends " +
                    "(name, uuid, friends, requests, allowRequest, allowJump, allowNotify) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1, player.getName());
            ps.setString(2, player.getUniqueId().toString());
            ps.setString(3, "");
            ps.setString(4, "");
            ps.setBoolean(5, true);
            ps.setBoolean(6, true);
            ps.setBoolean(7, true);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsPlayer(String player) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT * FROM friends WHERE name=?");
            ps.setString(1, player);
            ResultSet results = ps.executeQuery();
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setName(Player player) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET name=? WHERE uuid=?");
            ps.setString(1, player.getName());
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName(String uuid) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT name FROM friends WHERE uuid=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUUID(String name) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT uuid FROM friends WHERE name=?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("uuid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setOnline(Player player, boolean bool) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET online=? WHERE uuid=?");
            ps.setBoolean(1, bool);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getOnline(String uuidPlayer) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT online FROM friends WHERE uuid=?");
            ps.setString(1, uuidPlayer);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("online");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addFriend(String uuidPlayer, String uuidFriend) {
        try {
            String friendsString = getFriends(uuidPlayer);
            friendsString += uuidFriend + ";";
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET friends=? WHERE uuid=?");
            ps.setString(1, friendsString);
            ps.setString(2, uuidPlayer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFriend(String uuidPlayer, String uuidFriend) {
        try {
            String friendsString = getFriends(uuidPlayer);
            friendsString = friendsString.replaceAll(uuidFriend + ";", "");
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET friends=? WHERE uuid=?");
            ps.setString(1, friendsString);
            ps.setString(2, uuidPlayer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearFriends(String uuidPlayer) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET friends=? WHERE uuid=?");
            ps.setString(1, "");
            ps.setString(2, uuidPlayer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFriends(String uuidPlayer) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT friends FROM friends WHERE uuid=?");
            ps.setString(1, uuidPlayer);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("friends");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getFriendsList(String uuidPlayer) {
        String friendListRAW = getFriends(uuidPlayer);
        List<String> friendList = new ArrayList<>();
        if (friendListRAW.isEmpty()) return friendList;
        friendList = Arrays.asList(friendListRAW.split(";"));
        return friendList;
    }

    public int getFriendsCount(String uuidPlayer) {
        String friendList = getFriends(uuidPlayer);
        if (friendList.isEmpty()) return 0;
        String[] friendStringArray = friendList.split(";");
        return friendStringArray.length;
    }

    public void addRequest(String uuidPlayer, String uuidRequester) {
        try {
            String requestsString = getRequests(uuidPlayer);
            requestsString += uuidRequester + ";";
            PreparedStatement ps2 = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET requests=? WHERE uuid=?");
            ps2.setString(1, requestsString);
            ps2.setString(2, uuidPlayer);
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeRequest(String uuidPlayer, String uuidRequester) {
        try {
            String requestString = getRequests(uuidPlayer);
            requestString = requestString.replaceAll(uuidRequester + ";", "");
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET requests=? WHERE uuid=?");
            ps.setString(1, requestString);
            ps.setString(2, uuidPlayer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearRequests(Player player) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET requests=? WHERE uuid=?");
            ps.setString(1, "");
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getRequests(String uuidPlayer) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT requests FROM friends WHERE uuid=?");
            ps.setString(1, uuidPlayer);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("requests");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getRequestsList(String uuidPlayer) {
        String requestListRAW = getRequests(uuidPlayer);
        List<String> requestList = new ArrayList<>();
        if (requestListRAW.isEmpty()) return requestList;
        requestList = Arrays.asList(requestListRAW.split(";"));
        return requestList;
    }

    public int getRequestsCount(String uuidPlayer) {
        String requestList = getRequests(uuidPlayer);
        if (requestList.isEmpty()) return 0;
        String[] requestsStringArray = requestList.split(";");
        return requestsStringArray.length;
    }

    public void setAllowRequest(Player player, boolean bool) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET allowRequest=? WHERE uuid=?");
            ps.setBoolean(1, bool);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getAllowRequest(String uuidPlayer) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT allowRequest FROM friends WHERE uuid=?");
            ps.setString(1, uuidPlayer);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("allowRequest");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setAllowJump(Player player, boolean bool) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET allowJump=? WHERE uuid=?");
            ps.setBoolean(1, bool);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getAllowJump(String uuidPlayer) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT allowJump FROM friends WHERE uuid=?");
            ps.setString(1, uuidPlayer);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("allowJump");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setAllowNotify(Player player, boolean bool) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET allowNotify=? WHERE uuid=?");
            ps.setBoolean(1, bool);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getAllowNotify(String uuidPlayer) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT allowNotify FROM friends WHERE uuid=?");
            ps.setString(1, uuidPlayer);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getBoolean("allowNotify");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setLastOnline(Player player) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET lastOnline=? WHERE uuid=?");
            ps.setLong(1, System.currentTimeMillis());
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getLastOnline(String player) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT lastOnline FROM friends WHERE name=?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getLong("lastOnline");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 404;
    }


    public void setServer(Player player, String server) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("UPDATE friends SET server=? WHERE uuid=?");
            ps.setString(1, server);
            ps.setString(2, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getServer(String player) {
        try {
            PreparedStatement ps = plugin.mySQL.getConnection().prepareStatement("SELECT server FROM friends WHERE name=?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("server");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
