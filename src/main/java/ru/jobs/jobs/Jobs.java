package ru.jobs.jobs;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.logging.Logger;

public final class Jobs extends JavaPlugin {

    private final Logger logger = Logger.getLogger("Jobs");
    private final File config = new File(getDataFolder() + File.separator + "config.yml");
    private final boolean broadcastSwitcher = getConfig().getBoolean("console.console-broadcast");
    private final String dataFormat = getConfig().getString("database.format");
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private final String address = getConfig().getString("database.ip");
    private final String port = getConfig().getString("database.port");
    private final String username = getConfig().getString("database.username");
    private final String password = getConfig().getString("database.password");
    private final String database = getConfig().getString("database.database");
    private final String jdbcURL = "jdbc:mysql://" + address + ":" + port + "/" + database;
    private final String table = "CREATE TABLE IF NOT EXISTS `" + database + "`.`jobs` (" +
            "`id` INT NOT NULL AUTO_INCREMENT," +
            "`player` VARCHAR(30) NOT NULL," +
            "`job` VARCHAR(45) NULL," +
            "PRIMARY KEY (`id`, `player`));";

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

        if (dataFormat.equalsIgnoreCase("mysql")) {
            setConnection(jdbcURL, username, password);
            createTableMethod(table);
        }


    }

    @Override
    public void onDisable() {
        sendConsoleBroadcastOnDisable(logger);
        if (dataFormat.equalsIgnoreCase("mysql")) {
            closeConnection(con, stmt, rs);
        }
    }



    //TODO: Сделать более красивое сообщение о выключении
    private void sendConsoleBroadcastOnEnable(Logger sender) {
        sender.info("Plugin enabled");
    }

    //TODO: Сделать более красивое сообщение о выключении
    private void sendConsoleBroadcastOnDisable(Logger sender) {
        sender.info("Plugin disabled");
    }

    //is used to create a connection
    private void setConnection(String url, String login, String pass) {
        try {
            con = DriverManager.getConnection(url, login, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //is used as a shutdown method for mysql data in the onDisable method
    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    //is used to create mysql database table
    private void createTableMethod(String string) {
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(string);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
