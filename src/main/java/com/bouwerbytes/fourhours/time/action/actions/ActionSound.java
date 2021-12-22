package com.bouwerbytes.fourhours.time.action.actions;

import com.bouwerbytes.fourhours.time.action.Action;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ActionSound extends Action {
    private Sound sound;
    
    public ActionSound(String data) {
        super(data);
        try {
            this.sound = Sound.valueOf(data.toUpperCase());
        } catch (Exception ex) {
            Bukkit.getLogger().warning(String.format("" +
                    "[ServerRestart] Sound not found: `%s`.", data));
            valid = false;
        }
    }
    
    @Override
    public void execute() {
        if (!valid) return;
        for (Player player : Bukkit.getOnlinePlayers())
            player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
    }
}
