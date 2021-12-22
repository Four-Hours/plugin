package com.bouwerbytes.fourhours;

import java.util.logging.Logger;

import com.bouwerbytes.fourhours.time.Time;
import com.bouwerbytes.fourhours.time.Whitelist;

import org.bukkit.plugin.java.JavaPlugin;

public final class FourHours extends JavaPlugin {
    private Logger logger;
    private Database db;
    private Config config;
    private Time time;
    private Whitelist whitelist;

    public void onEnable() {
        logger = getLogger();
        logger.info("Enabling FourHours");

        config = new Config(this);

        db = new Database(config.getConfigFile(), logger);
        db.setup();

        whitelist = new Whitelist(this);

        time = new Time(this);
        time.nextSchedule();
    }

    @Override
    public void onDisable() {
        db.disconnect();
        time.stopSchedule();
        getLogger().info("Disabled FourHours");
    }

    public Config config() {
        return config;
    }

    public Whitelist whitelist() {
        return whitelist;
    }
}