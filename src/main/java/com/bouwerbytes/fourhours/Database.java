package com.bouwerbytes.fourhours;

import java.sql.*;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

public class Database {

    private Connection conn;
    private FileConfiguration configFile;
    private Logger logger;

    public Database() {
        App app = new App();
        configFile = app.getConfig();
        logger = app.getLogger();
    }
    
    public void Connect() throws SQLException {
        //create connection for a server installed in localhost, with a user "root" with no password
        //conn = DriverManager.getConnection("jdbc:mariadb://localhost/", "root", null); 
    }
}
