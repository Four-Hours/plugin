package com.bouwerbytes.fourhours.time.action;

import com.bouwerbytes.fourhours.FourHours;
import com.bouwerbytes.fourhours.time.action.actions.*;

import org.bukkit.Bukkit;

public class ActionParser {
    public static Action parseAction(String data, FourHours plugin) {
        try {
            String[] dataArr = data.split(" \\| ", 2);
            switch (dataArr[0].toLowerCase()) {
                case "chat":
                    return new ActionChat(dataArr[1]);
                case "command":
                    return new ActionCommand(dataArr[1]);
                case "sound":
                    return new ActionSound(dataArr[1]);
                case "title":
                    return new ActionTitle(dataArr[1]);
                case "whitelist":
                    return new ActionWhitelist(plugin);
                default:
                    throw new IllegalArgumentException();
            }
        } catch (Exception ex) {
            Bukkit.getLogger().warning(String.format("" +
                    "[FourHours] Invalid action format: `%s`.",
                    data.replace('§', '&')));
            return new ActionEmpty("");
        }
    }

}
