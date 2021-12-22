package com.bouwerbytes.fourhours.time.action.actions;

import com.bouwerbytes.fourhours.time.action.Action;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionTitle extends Action {
    private String title, subtitle;
    private int fadeIn, stay, fadeOut;
    
    public ActionTitle(String data) {
        super(data);
        try {
            String[] dataArr = data.split(" :: ");
            this.title = dataArr[0];
            this.subtitle = dataArr[1];
            
            String[] ticksArr = dataArr[2].split(" ");
            this.fadeIn = Integer.parseInt(ticksArr[0]);
            this.stay = Integer.parseInt(ticksArr[1]);
            this.fadeOut = Integer.parseInt(ticksArr[2]);
        } catch (Exception ex) {
            Bukkit.getLogger().warning(String.format("" +
                    "[ServerRestart] Invalid title format: `%s`.",
                    data.replace('§', '&')));
            valid = false;
        }
    }
    
    @Override
    public void execute() {
        if (!valid) return;
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }
}
