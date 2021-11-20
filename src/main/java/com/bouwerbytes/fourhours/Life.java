package com.bouwerbytes.fourhours;

import org.bukkit.entity.Player;

public class Life {

    //TODO Add database connection
    //TODO Use Spigot API
    public int addLives(int lives, int add, Player player) {
        int newLives = lives + add;
        return newLives;
    }
}