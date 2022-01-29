package ru.jobs.jobs;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.logging.Logger;

public final class Jobs extends JavaPlugin {

    private final Logger logger = Logger.getLogger("Jobs");
    private final File config = new File(getDataFolder() + File.separator + "config.yml");
    private boolean broadcastSwitcher = getConfig().getBoolean("console.console-broadcast");
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private final String address = getConfig().getString("database.ip");
    private final String port = getConfig().getString("database.port");
    private final String username = getConfig().getString("database.username");
    private final String password = getConfig().getString("database.password");
    private final String database = getConfig().getString("database.database");
    private String jdbcURL = "jdbc:mysql://" + address + ":" + port + "/" + database;

    @Override
    public void onEnable() {
        //Test for configuration
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
        //Logic for broadcastSwitcher
        if (broadcastSwitcher) {
            sendConsoleBroadcastOnEnable(logger);
        }


    }

    @Override
    public void onDisable() {
        sendConsoleBroadcastOnDisable(logger);
    }

    private void sendConsoleBroadcastOnEnable(Logger sender) {
        //Сделай еще броадкаст
        sender.info("Plugin enabled");
    }

    private void sendConsoleBroadcastOnDisable(Logger sender) {
        //Сделай еще броадкаст
        sender.info("Plugin disabled");
    }

    private void setConnection(Connection connection, String url, String login, String pass) {
            connection = DriverManager.getConnection(url, login, pass);
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException se) {
        }
    }
}
