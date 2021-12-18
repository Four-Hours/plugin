package com.bouwerbytes.fourhours;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {
    private FileConfiguration configFile;
    private Logger logger;
    private Database db;

    public void onEnable() {
        logger = getLogger();
        logger.info("Enabling FourHours");

        saveDefaultConfig();
        configFile = getConfig();

        db = new Database(configFile, logger);
        db.setup();
    }

    @Override
    public void onDisable() {
        db.disconnect();
        getLogger().info("See you again, SpigotMC!");
    }
}