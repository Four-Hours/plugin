package com.bouwerbytes.fourhours;

import java.sql.*;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

// TODO add update method
public class Database {

    private Connection conn = null;
    private FileConfiguration config;
    private Logger logger;

    public Database(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
        Connect();
    }
    
    // TODO logging and comments
    public void Connect() {
        // Reads relevant data from config file
        String host = config.getString("MySQL.Host");
        int port = config.getInt("MySQL.Port");
        String user = config.getString("MySQL.Username");
        final String pass = config.getString("MySQL.Password");
        String dbName = config.getString("MySQL.DB-Name");

        //create connection for a server installed in localhost, with a user "root" with no password
        //conn = DriverManager.getConnection("jdbc:mariadb://localhost/", "root", null); 
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        logger.info("Using jdbc url: " + url);
        
        try {
            //
            conn = DriverManager.getConnection(url, user, pass);
            logger.info("Connected to DB succesfully :)");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // TODO Actually make this query the connection
    public ResultSet Query(String query) {
        ResultSet temp = null;
        return temp;
    }
}
