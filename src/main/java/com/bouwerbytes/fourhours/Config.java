package com.bouwerbytes.fourhours;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.bouwerbytes.fourhours.time.action.Action;
import com.bouwerbytes.fourhours.time.action.ActionParser;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    
    // **************************************************
    // * Fields                                         *
    // **************************************************

    // Functionality fields.
    private FileConfiguration config;
    private Logger logger;
    private FourHours plugin;

    // Other Fields
    private List<Cron> cronList;
    private Map<Integer, List<Action>> actionMap;

    /**
    * Configuration class capable of multiple different config parsing jobs.
    *
    * @param plugin
    */
    public Config(FourHours plugin) {
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
        this.logger = plugin.getLogger();
        this.plugin = plugin;
        
        cronParser();
        actionMapParser();
    }
    


    // **************************************************
    // * Getters                                        *
    // **************************************************

    public FileConfiguration getConfigFile() {
        return config;
    }
    
    public List<Cron> getCronList() {
        return cronList;
    }
    
    public Map<Integer, List<Action>> getActionMap() {
        return actionMap;
    }



    // **************************************************
    // * Private methods                                *
    // **************************************************

    private void cronParser() {
        cronList = new ArrayList<>();
        CronDefinition definition = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
        for (String cronTime : config.getStringList("Time.Schedule")) {
            try {
                Cron cron = new CronParser(definition).parse(cronTime);
                cronList.add(cron);
            } catch (Exception ex) {
                logger.warning(String.format("" +
                        "[FourHours] Cron time is invalid: `%s`.", cronTime));
            }
        }
    }

    private void actionMapParser() {
        actionMap = new LinkedHashMap<>();
        ConfigurationSection actionListSection = config.getConfigurationSection("Time.Actions");
        for (String secondsStr : actionListSection.getKeys(false)) {
            int seconds = Integer.parseInt(secondsStr);
            List<Action> actionList = new ArrayList<>();
            for (String actionData : actionListSection.getStringList(secondsStr))
                actionList.add(ActionParser.parseAction(actionData, plugin));
            actionMap.put(seconds, actionList);
        }
    }
}
