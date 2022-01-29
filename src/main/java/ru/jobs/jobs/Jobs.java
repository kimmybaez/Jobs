package ru.jobs.jobs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class Jobs extends JavaPlugin {

    private final Logger logger = Logger.getLogger("Jobs");
    private final File config = new File(getDataFolder() + File.separator + "config.yml");
    private boolean broadcastFactor = getConfig().getBoolean("console.console-broadcast");

    @Override
    public void onEnable() {

        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        if (broadcastFactor) {
            sendConsoleBroadcast(logger);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void sendConsoleBroadcast(Logger sender) {
        //Сделай еще броадкаст
        sender.info("Plugin enabled");
    }
}
