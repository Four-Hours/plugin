package com.bouwerbytes.fourhours.time.action.actions;

import com.bouwerbytes.fourhours.time.action.Action;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionChat extends Action {
    private String message;
    
    public ActionChat(String data) {
        super(data);
        this.message = data;
    }
    
    @Override
    public void execute() {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(message);
    }

}
