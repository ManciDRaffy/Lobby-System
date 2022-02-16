package de.laudytv.lobbysystem;

import com.comphenix.protocol.ProtocolManager;
import de.laudytv.lobbysystem.automessage.*;
import de.laudytv.lobbysystem.build.BuildCommand;
import de.laudytv.lobbysystem.build.BuildManager;
import de.laudytv.lobbysystem.commands.DebugCommand;
import de.laudytv.lobbysystem.commands.FunCommand;
import de.laudytv.lobbysystem.doublejump.DoubleJump;
import de.laudytv.lobbysystem.fly.FlyCommand;
import de.laudytv.lobbysystem.friends.FriendClickListener;
import de.laudytv.lobbysystem.friends.sql.FriendSQLGetter;
import de.laudytv.lobbysystem.gamemode.GameModeCommand;
import de.laudytv.lobbysystem.hideplayers.HidePlayersListener;
import de.laudytv.lobbysystem.hideplayers.HidePlayersManager;
import de.laudytv.lobbysystem.hideplayers.HidePlayersSQLGetter;
import de.laudytv.lobbysystem.listeners.JoinListener;
import de.laudytv.lobbysystem.listeners.LobbyListener;
import de.laudytv.lobbysystem.listeners.WeatherChangeListener;
import de.laudytv.lobbysystem.navigator.NavigatorListener;
import de.laudytv.lobbysystem.nick.NickCommand;
import de.laudytv.lobbysystem.nick.UnNickCommand;
import de.laudytv.lobbysystem.serverselector.ServerSelectorListener;
import de.laudytv.lobbysystem.serverselector.ServerSelectorSQLGetter;
import de.laudytv.lobbysystem.sql.Language;
import de.laudytv.lobbysystem.sql.MySQL;
import de.laudytv.lobbysystem.sql.PermissionsSQLGetter;
import de.laudytv.lobbysystem.sql.WarpSQLGetter;
import de.laudytv.lobbysystem.tablist.TabVersionWrapper;
import de.laudytv.lobbysystem.tablist.TablistSQLGetter;
import de.laudytv.lobbysystem.util.PlayerDataSQL;
import de.laudytv.lobbysystem.util.PluginMessage;
import de.laudytv.lobbysystem.vanish.VanishCommand;
import de.laudytv.lobbysystem.vanish.VanishManager;
import de.laudytv.lobbysystem.warp.commands.DelWarpCommand;
import de.laudytv.lobbysystem.warp.commands.SetWarpCommand;
import de.laudytv.lobbysystem.warp.commands.WarpCommand;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.Objects;

public class LobbySystem extends JavaPlugin {

    private static LobbySystem plugin;
    public BukkitRunnable tablistUpdater;
    public BukkitRunnable onlineplayerssaver;
    public BukkitRunnable hidePlayersUpdater;

    public MySQL mySQL;
    public FriendSQLGetter friendData;
    public AutoMessageSQLGetter amData;
    public ServerSelectorSQLGetter ssData;
    public PermissionsSQLGetter permissions;
    public HidePlayersSQLGetter hideData;
    public TablistSQLGetter tabData;
    public WarpSQLGetter warpData;
    public PlayerDataSQL playerData;
    public Language lang;

    public ProtocolManager protocolManager;
    public LuckPerms api;

    private PluginMessage pluginMessage;

    private VanishManager vanishManager;

    public static LobbySystem getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        // mysql & getter
        this.mySQL = new MySQL();
        initializeSQLGetter();

        // try to connect to databases
        BukkitRunnable retrySQLConnection = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    mySQL.connect();
                } catch (SQLException | ClassNotFoundException throwables) {
                    Bukkit.getLogger().info("[!!!WARNING!!!] LOBBYSYSTEM couldn't connect to databases, trying again in 20 seconds!");
                }
                if (mySQL.isConnected() && mySQL.isLanguageConnected()) {
                    cancel();
                    Bukkit.getLogger().info("LOBBYSYSTEM successfully connected to databases!");
                    friendData.createTable();
                    amData.createTable();
                    amData.createSettingsTable();
                    ssData.createItemTable();
                    ssData.createSettingTable();
                    hideData.createTable();
                    tabData.createTable();
                    warpData.createWarpTable();
                    warpData.createNavTable();
                    warpData.createSettingTable();
                }
            }
        };
        retrySQLConnection.runTaskTimer(this, 0, 20 * 20);

        // manager
        vanishManager = new VanishManager(this);
        HidePlayersManager hidePlayersManager = new HidePlayersManager(this);

        // command & listener
        VanishCommand vanishCommand = new VanishCommand();

        // listener
        PluginManager pM = Bukkit.getPluginManager();
        pM.registerEvents(new DoubleJump(), this);
        pM.registerEvents(vanishCommand, this);
        pM.registerEvents(new JoinListener(), this);
        pM.registerEvents(new ServerSelectorListener(), this);
        pM.registerEvents(new LobbyListener(), this);
        pM.registerEvents(new HidePlayersListener(), this);
        pM.registerEvents(new FriendClickListener(), this);
        pM.registerEvents(new NavigatorListener(), this);
        pM.registerEvents(new WeatherChangeListener(), this);

        // plugin message bungeecord
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());
        pluginMessage = new PluginMessage();

        // commands
        Objects.requireNonNull(getCommand("build")).setExecutor(new BuildCommand());
        Objects.requireNonNull(getCommand("vanish")).setExecutor(vanishCommand);
        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyCommand());
        Objects.requireNonNull(getCommand("gamemode")).setExecutor(new GameModeCommand());
        Objects.requireNonNull(getCommand("debug")).setExecutor(new DebugCommand());
        Objects.requireNonNull(getCommand("setwarp")).setExecutor(new SetWarpCommand());
        Objects.requireNonNull(getCommand("delwarp")).setExecutor(new DelWarpCommand());
        Objects.requireNonNull(getCommand("warp")).setExecutor(new WarpCommand());
        Objects.requireNonNull(getCommand("automessage")).setExecutor(new AutoMessageCommand());
        Objects.requireNonNull(getCommand("nick")).setExecutor(new NickCommand());
        Objects.requireNonNull(getCommand("unnick")).setExecutor(new UnNickCommand());
        Objects.requireNonNull(getCommand("fun")).setExecutor(new FunCommand());

    }

    @Override
    public void onDisable() {
        mySQL.disconnect();
        // plugin message bungeecord
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", new PluginMessage());

        // R U N N A B L E S   disable
        onlineplayerssaver.cancel();
        hidePlayersUpdater.cancel();
        tablistUpdater.cancel();

        // AUTO MESSAGE DISABLE
        ChatPlayer.cancel();
        BossBarPlayer.cancel();
        ActionBarPlayer.cancel();


        // P L U G I N   H O O K S
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null)
            api = provider.getProvider();

        //RUNNABLES
        onlineplayerssaver = new BukkitRunnable() {
            @Override
            public void run() {
                pluginMessage.saveAllServers();
                pluginMessage.saveAllPlayers();
            }
        };
        onlineplayerssaver.runTaskTimer(this, 0, 1);

        hidePlayersUpdater = new BukkitRunnable() {
            @Override
            public void run() {
                HidePlayersManager.hideAll();
            }
        };
        hidePlayersUpdater.runTaskTimer(this, 0, 5);

        tablistUpdater = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(TabVersionWrapper::setTablist);
            }
        };
        tablistUpdater.runTaskTimer(this, 0, 5);

        // AUTO MESSAGE ENABLE TASKS
        ChatPlayer.run();
        BossBarPlayer.run();
        ActionBarPlayer.run();

        // T A B L I S T
        TabVersionWrapper tabVersionWrapper = TabVersionWrapper.getInstance();

    }

    /**
     * @return vanish manager
     */
    public VanishManager getVanishManager() {
        return vanishManager;
    }

    private void initializeSQLGetter() {
        this.friendData = new FriendSQLGetter(this);
        this.amData = new AutoMessageSQLGetter(this);
        this.ssData = new ServerSelectorSQLGetter(this);
        this.permissions = new PermissionsSQLGetter(this);
        this.hideData = new HidePlayersSQLGetter(this);
        this.tabData = new TablistSQLGetter(this);
        this.warpData = new WarpSQLGetter(this);
        this.playerData = new PlayerDataSQL(this);
        this.lang = new Language(this);
    }

    public PluginMessage getPluginMessage() {
        return pluginMessage;
    }
}
