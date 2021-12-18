package com.bouwerbytes.fourhours;

import java.sql.*;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

//TODO Create specialized methods for each table
public class Database {

    private FileConfiguration config;
    private Logger logger;
    
    private Connection conn = null;
    protected Statement st;
    protected ResultSet rs;

    public Database(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }
    
    private void connect() {
        // Reads relevant data from config file
        String host = config.getString("MySQL.Host");
        int port = config.getInt("MySQL.Port");
        String user = config.getString("MySQL.Username");
        final String pass = config.getString("MySQL.Password");
        String dbName = config.getString("MySQL.DB-Name");

        //create connection for a server installed in localhost, with a user "root" with no password
        //conn = DriverManager.getConnection("jdbc:mariadb://localhost/", "root", null); 
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        
        try {
            conn = DriverManager.getConnection(url, user, pass);
            logger.info("Connected to DB succesfully");
        } catch (SQLException e) {
            //! Might be a good idea to do better error mitigation
            e.printStackTrace();
        }

    }

    // Closes all extra objects
    public void disconnect() {
        try {
            if(rs!=null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(st!=null) st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(conn!=null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Returns ResultSet from MySQL compatible query to database
    //! This is a very generalized method, more specialized will come in the future :)
    //! Only use this if no other methods will work
    public ResultSet query(String query) {
        // Resets required variables
        try {
            if(rs!=null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(st!=null) st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Connects to the database if no connection exists
        if(conn == null) connect();

        // Create JDBC statement object
        // Queries statement
        try {
            if(conn != null) {
                st = conn.createStatement();
            }
            if(st != null) {
                rs = st.executeQuery(query);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    // Creates database tables if they do not exist
    public void setup() {
        String playerTable = "players(id VARCHAR(255), lives int, team int, last_connection int(11));";
        String sql = "CREATE TABLE IF NOT EXISTS ";

        // Connects to the database if no connection exists
        if(conn == null) connect();
        try {
            if(conn != null) {
                st = conn.createStatement();
            }
            if(st != null) {
                st.executeUpdate(sql + playerTable);
                //TODO Incude more tables when needed

                logger.info("Configured Database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
