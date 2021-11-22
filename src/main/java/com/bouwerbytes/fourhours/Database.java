package com.bouwerbytes.fourhours;

import java.sql.*;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

// TODO Figure out how connecting and var storage works
public class Database {

    private Connection conn;
    private FileConfiguration configFile;
    private Logger logger;
    private Statement stmt;

    public Database() {
        App app = new App();
        configFile = app.getConfig();
        logger = app.getLogger();
    }
    
    //TODO Actually connect lmao
    public void Connect(String host, int port, String user, String pass) throws SQLException {
        //create connection for a server installed in localhost, with a user "root" with no password
        //conn = DriverManager.getConnection("jdbc:mariadb://localhost/", "root", null); 
    }

    // TODO Actually make this query the connection
    public ResultSet Query(String query) {
        ResultSet temp = null;
        return temp;
    }
}
