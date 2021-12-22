package com.bouwerbytes.fourhours.time.action.actions;

import com.bouwerbytes.fourhours.time.action.Action;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ActionCommand extends Action {
    private static CommandSender sender = Bukkit.getConsoleSender();
    private String command;
    
    public ActionCommand(String data) {
        super(data);
        this.command = data;
    }
    
    @Override
    public void execute() {
        Bukkit.dispatchCommand(sender, command);
    }
}
