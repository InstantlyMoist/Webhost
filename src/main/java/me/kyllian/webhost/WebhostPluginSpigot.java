package me.kyllian.webhost;

import me.kyllian.webhost.commands.WebhostExecutor;
import me.kyllian.webhost.files.FileLoaderSpigot;
import me.kyllian.webhost.web.ServerHandler;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public class WebhostPluginSpigot extends JavaPlugin {

    private ServerHandler serverHandler;

    @Override
    public void onEnable() {
        super.onEnable();
        
        this.saveDefaultConfig();

        FileLoaderSpigot.ensureIndexPopulated(this);

        new Metrics(this, 9354);

        getCommand("webhost").setExecutor(new WebhostExecutor(this));
        
        resetHandler();

    }
    
    public void resetHandler() {
        serverHandler = new ServerHandler();

        fireServer();
    }

    public void fireServer() {
        try {
            serverHandler.fire(getDataFolder(),
                    getConfig().getInt("port"),
                    getConfig().getString("resource_base"),
                    getConfig().getStringList("welcome_files").toArray(new String[0]));
        } catch (Exception exception) {
            Bukkit.getLogger().warning("Firing web server FAILED");
            if (getConfig().getInt("port") == 80) {
                Bukkit.getLogger().warning("Default port failed... Attempting different one!");
                int port = ThreadLocalRandom.current().nextInt(1023, 49151);
                getConfig().set("port", port);
                saveConfig();
                Bukkit.getLogger().warning("Attempting refire with port " + port);
                fireServer();
            }
            Bukkit.getLogger().warning("Firing web server FAILED, Please contact support: https://discord.gg/zgKr2YM");
            exception.printStackTrace();
        }
    }
    
    public ServerHandler getServerHandler() {
    	return this.serverHandler;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        serverHandler.stop();
    }
}
