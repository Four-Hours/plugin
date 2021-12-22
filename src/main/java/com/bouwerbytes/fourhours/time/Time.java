package com.bouwerbytes.fourhours.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bouwerbytes.fourhours.Config;
import com.bouwerbytes.fourhours.FourHours;
import com.bouwerbytes.fourhours.time.action.Action;
import com.cronutils.model.Cron;
import com.cronutils.model.time.ExecutionTime;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class Time implements Listener{

    // **************************************************
    // * Fields                                         *
    // **************************************************
    
    // Functionality Fields
    private FourHours plugin;
    private Config config;

    // Other Fields
    private List<Cron> cronList;
    private Boolean enabled;
    private LocalDateTime nextTriggerTime;
    private ScheduledExecutorService executor;

    /**
    * Class that handles scheduling of the whitelist through cron schedule parsing and executing.
    * Heavily based on i0HeX's ServerRestart Plugin (linked).
    * @see https://gitlab.com/i0xHeX/ServerRestart/-/tree/master
    *
    * @param plugin
    */
    public Time(FourHours plugin) {
        this.plugin = plugin;
        this.config = plugin.config();
        this.cronList = config.getCronList();
        this.enabled = config.getConfigFile().getBoolean("Time.Enabled");
    }



    // **************************************************
    // * Public methods                                 *
    // **************************************************

    //! Call on enable
    public void nextSchedule() {
        if (enabled) {
            stopSchedule();
            nextTriggerTime = null;
            getNextDelayMillis().ifPresent(this::schedule);
        }
    }

    //! Call on disable
    public void stopSchedule() {
        if (executor != null) executor.shutdownNow();
    }



    // **************************************************
    // * Getters                                        *
    // **************************************************

    public LocalDateTime getNextTriggerTime() {
        return nextTriggerTime;
    }


    
    // **************************************************
    // * Private methods                                *
    // **************************************************

    private void schedule(long millisToTrigger) {
        nextTriggerTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis() + millisToTrigger),
                ZoneId.systemDefault());
    
        executor = Executors.newSingleThreadScheduledExecutor();
        plugin.config().getActionMap().forEach((seconds, actions) -> {
            long millisToTask = millisToTrigger - (seconds * 1000);
            if (millisToTask < 0) return;

            executor.schedule(() -> {
                runSync(() -> actions.forEach(Action::execute));
            }, millisToTask, TimeUnit.MILLISECONDS);
        });

        executor.schedule(() -> runSync(this::nextSchedule),
                millisToTrigger, TimeUnit.MILLISECONDS);
    }

    private Optional<Long> getNextDelayMillis() {
        return getNextDelayMillis(ZonedDateTime.now());
    }

    private Optional<Long> getNextDelayMillis(ZonedDateTime timeAfter) {
        long currentMillis = System.currentTimeMillis();

        // select next nearest time
        long minDelayMillis = -1;
        for(Cron cron : cronList) {
            Optional<ZonedDateTime> time = ExecutionTime.forCron(cron).nextExecution(timeAfter);
            if (time.isPresent()) {
                long delayMillis = time.get().toInstant().toEpochMilli() - currentMillis;
                if (delayMillis < minDelayMillis || minDelayMillis == -1) minDelayMillis = delayMillis;
            }
        }
        return minDelayMillis == -1 ? Optional.empty() : Optional.of(minDelayMillis + 1);
    }

    private void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

}
