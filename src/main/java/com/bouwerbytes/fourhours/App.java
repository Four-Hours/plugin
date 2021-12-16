package com.bouwerbytes.fourhours;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    private FileConfiguration configFile;
    private Logger logger;

    // Constructor
    public App() {
        //configFile = getConfig();
    }

    public void onEnable() {
        logger = getLogger();
        getLogger().info("Hello, SpigotMC!");
        saveDefaultConfig();
        configFile = getConfig();

        info("About to load db");
        Database db = new Database(configFile, logger);
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }
    
    public void info(String msg) {
        getLogger().info(msg);
    }

    public void warn(String msg) {
        getLogger().warning(msg);
    }

    // Getters
    //public FileConfiguration getConfig() { return configFile; }
}