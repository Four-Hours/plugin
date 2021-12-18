package com.bouwerbytes.fourhours;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.bukkit.configuration.file.FileConfiguration;

//TODO Create specialized methods for each table
//! If the commenting is too much lemme know, was having some fun but i can understand if it is distracting.
public class Database {

    // **************************************************
    // * Fields                                         *
    // **************************************************

    // Functionality fields.
    private FileConfiguration config;
    private Logger logger;
    
    // JDBC fields.
    private Connection conn = null;
    protected Statement st;
    protected ResultSet rs;

    //Config values.
    private String host;
    private int port;
    private String user;
    private String pass;
    private String dbName;

    /**
    * Constructor
    *
    * @param config FileConfiguration of the main config file.
    * @param logger Logger to be used by the class.
    */
    public Database(FileConfiguration config, Logger logger) {
        this.config = config;
        this.logger = logger;
        readConfig(config);
    }
    


    // **************************************************
    // * Private methods                                *
    // **************************************************

    private void readConfig(FileConfiguration config) {
        // Reads relevant data from config file
        host = config.getString("Database.Host");
        port = config.getInt("Database.Port");
        user = config.getString("Database.Username");
        pass = config.getString("Database.Password");
        dbName = config.getString("Database.DB-Name");
    }

    // Connects to the database specified in the config file.
    private void connect() {
        if(Stream.of(host, port, user, pass, dbName).allMatch(x -> x == null)) readConfig(config);
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        
        try {
            conn = DriverManager.getConnection(url, user, pass);
            logger.info("Connected to DB succesfully");
        } catch (SQLException e) {
            //! Might be a good idea to do better error mitigation.
            e.printStackTrace();
        }

    }

    // Queries an int from the database, used by getters who return an int.
    private int getInt(String sql) {
        ResultSet rset = query(sql);
        try {
            if(rset.next()) {
                return rset.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Updates an int in the database, used by setters who update an int.
    private int setInt(String sql) { return update(sql); }



    // **************************************************
    // * Public methods                                 *
    // **************************************************

    /**
     * Closes all objects owned by the instance.
     * Should be called before server shutdown.
     */
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

    /**
    * Sends a query to the database server.
    *
    * @apiNote THIS IS A VERY POWERFUL AND UNSPECIALIZED METHOD, ONLY USE IF NO GETTERS/SETTERS EXIST FOR WHAT YOU NEED.
    *
    * @param query The SQL compatable query to send to the server.
    *
    * @return ResultSet from the database server.
    */
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

        // Creates JDBC statement object
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

    /**
    * Sends an update query to the database server using JDBC executeUpdate().
    *
    * @apiNote THIS IS A VERY POWERFUL AND UNSPECIALIZED METHOD, ONLY USE IF NO GETTERS/SETTERS EXIST FOR WHAT YOU NEED.
    *
    * @param query The SQL compatable query to send to the server.
    *
    * @return 1 if update was succesful, -1 if an issue occured.
    */
    public int update(String query) {
        // Resets required variables
        try {
            if(rs!=null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        try {
            if(st!=null) st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
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
                return st.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    /**
     * Creates all database tables if they do not exist.
     */
    public void setup() {
        String playerTable = "players(uuid VARCHAR(255), lives int, last_connection int(11));";
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



    // **************************************************
    // * Getters                                        *
    // **************************************************

    /**
    * Gets the number of lives a player has.
    *
    * @param uuid UUID of the player to lookup.
    *
    * @return Lives of the player, -1 if player not found.
    */
    public int getPlayerLives(UUID uuid) { return getInt("SELECT lives FROM players WHERE uuid = '" + uuid + "';"); }

    //! Unsure how to store this, or if it is even needed. Figured it could be useful.
    /**
    * Gets the UTC timecode of the last known connection of the player.
    *
    * @param uuid UUID of the player to lookup.
    *
    * @return UTC timecode, -1 if player not found.
    */
    public int getPlayerLastConnection(UUID uuid) { return getInt("SELECT last_connection FROM players WHERE uuid = '" + uuid + "';"); }



    // **************************************************
    // * Setters                                        *
    // **************************************************

    /**
    * Sets the number of lives a player has.
    *
    * @param lives Number of lives the player should have.
    * @param uuid UUID of the player to lookup.
    *
    * @return 1 if update was successful, -1 if player not found or another issue occured.
    */
    public int setPlayerLives(int lives, UUID uuid) { return setInt("UPDATE players SET lives = " + lives + " WHERE uuid = '" + uuid + "';"); }

    /**
    * Sets the UTC timecode of the last known connection of the player.
    *
    * @param time Last known connection time of the player.
    * @param uuid UUID of the player to lookup.
    *
    * @return 1 if update was successful, -1 if player not found or another issue occured.
    */
    public int setPlayerLastConnection(int time, UUID uuid) { return setInt("UPDATE players SET last_connection = " + time + " WHERE uuid = '" + uuid + "';"); }
}
