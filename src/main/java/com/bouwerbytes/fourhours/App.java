package com.bouwerbytes.fourhours;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    private FileConfiguration configFile;
    private Logger logger;

    // Constructor
    public App() {
        configFile = getConfig();
        logger = getLogger();
    }

    public void onEnable() {
        getLogger().info("Hello, SpigotMC!");
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }
    
    // Getters
    public FileConfiguration getConfig() { return configFile; }
    public Logger getLogger() { return logger; }
}