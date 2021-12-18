package com.bouwerbytes.fourhours;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Life {
    private Database db;

    public Life() {

    }
    
    //TODO Add database connection
    //TODO Use Spigot API
    public int addLives(int add, UUID playerUUID) throws SQLException {
        // ? maybe wrong column name
        String column = "'Lives'";
        String table = "'Players'";
        String query = "SELECT "+ column +" FROM "+ table + " WHERE UUID = " + playerUUID;
        
        ResultSet rs = db.Query(query);
        rs.first();
        int oldLives = rs.getInt(1);
        int newLives = oldLives + add;

        //TODO add update
        
        return newLives;
    }
}