package de.laudytv.lobbysystem.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private Connection connection;
    private Connection languageConnection;

    /**
     * @return is connected
     */
    public boolean isConnected() {
        return connection != null;
    }

    /**
     * @return is language connected
     */
    public boolean isLanguageConnected() {
        return languageConnection != null;
    }

    /**
     * connects to databases
     *
     * @throws SQLException if connection is not possible
     */
    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        if (isConnected() && isLanguageConnected()) return;
        String host = "";
        String port = "";
        String database = "";
        String languageDatabase = "";
        String username = "";
        String password = "";
        if (!isConnected())
            connection = DriverManager.getConnection("jdbc:mariadb://" +
                            host + ":" + port + "/" + database,
                    username, password);
        if (!isLanguageConnected())
            languageConnection = DriverManager.getConnection("jdbc:mariadb://" +
                            host + ":" + port + "/" + languageDatabase,
                    username, password);
    }

    /**
     * disconnects the mysql connections
     */
    public void disconnect() {
        if (!isConnected() && !isLanguageConnected()) return;
        try {
            if (isConnected())
                connection.close();
            if (isLanguageConnected())
                languageConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * @return the language connection
     */
    public Connection getLanguageConnection() {
        return languageConnection;
    }

}


