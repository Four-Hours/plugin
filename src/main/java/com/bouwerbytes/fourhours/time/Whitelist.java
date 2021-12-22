package com.bouwerbytes.fourhours.time;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bouwerbytes.fourhours.FourHours;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Whitelist implements Listener{

    // **************************************************
    // * Fields                                         *
    // **************************************************
    
    // Functionality Fields
    // Just keeping this here until final release, will probably need it.
    private FourHours plugin;

    // Other Fields
    public boolean enabled;

    private String message;

    /**
    * Class that handles the custom whitelist. Custom whitelist still uses
    * minecraft's built in whitelist to manage the list of whitelisted players,
    *
    * @param plugin
    */
    public Whitelist(FourHours plugin) {
        this.plugin = plugin;
        this.message = plugin.getConfig().getString("Time.KickMessage");
    }
    


    // **************************************************
    // * Public methods                                 *
    // **************************************************

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(enabled) {
            List<UUID> uuids = new ArrayList<>();
            for(OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
                uuids.add(p.getUniqueId());
            }
            if(!uuids.contains(player.getUniqueId())){
                player.kickPlayer(message);
            }
        }
    }

    /**
    * Toggles the custom whitelist.
    */
    public void toggle() {
        if(enabled) {
            setWhitelist(false);
        } else {
            setWhitelist(true);
        }
    }

    /**
    * Kicks all non whitelisted players.
    */
    public void kickPlayers() {
        List<UUID> uuids = new ArrayList<>();
        for(OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
            uuids.add(p.getUniqueId());
        }
        for(Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()){
            if(!uuids.contains(onlinePlayer.getUniqueId())){
                onlinePlayer.kickPlayer(message);
            }
        }
    }

    /**
    * Sets the custom whitelist.
    * @param state State to set the whitelist to.
    */
    public void setWhitelist(boolean state) {
        enabled = state;
        kickPlayers();
    }
}
