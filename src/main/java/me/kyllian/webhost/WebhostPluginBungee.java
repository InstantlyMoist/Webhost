package me.kyllian.webhost;

import me.kyllian.webhost.files.FileLoaderBungee;
import me.kyllian.webhost.web.ServerHandler;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.bstats.bungeecord.Metrics;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class WebhostPluginBungee extends Plugin {

    private ServerHandler serverHandler;
    private Configuration configuration;

    @Override
    public void onEnable() {
        super.onEnable();

        new Metrics(this, 9362);

        FileLoaderBungee.ensureIndexPopulated(this, getDataFolder());

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

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

    private Configuration getConfig() {
        return configuration;
    }

    private void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDataFolder(), "config.yml"));
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
