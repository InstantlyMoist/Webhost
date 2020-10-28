package me.kyllian.webhost;

import me.kyllian.webhost.files.FileLoader;
import me.kyllian.webhost.web.ServerHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WebhostPlugin extends JavaPlugin {

    private ServerHandler serverHandler;

    @Override
    public void onEnable() {
        super.onEnable();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        FileLoader.ensureIndexPopulated(this);

        serverHandler = new ServerHandler(this);
        try {
            serverHandler.fire();
        } catch (Exception exception) {
            Bukkit.getLogger().warning("Firing web server FAILED, Please contact support: https://discord.gg/zgKr2YM");
            exception.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        serverHandler.stop();
    }
}
