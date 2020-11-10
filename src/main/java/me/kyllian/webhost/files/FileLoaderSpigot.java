package me.kyllian.webhost.files;

import me.kyllian.webhost.WebhostPluginSpigot;
import org.bukkit.Bukkit;

import java.io.File;

public class FileLoaderSpigot {

    public static void ensureIndexPopulated(WebhostPluginSpigot plugin) {
        File htmlFolder = new File(plugin.getDataFolder(), "html");
        if (!htmlFolder.exists()) { // Default page does not exist, just generate a new one
            Bukkit.getLogger().info("Website not found... Populating now");
            htmlFolder.mkdirs();
            new File(plugin.getDataFolder(), "html/assets").mkdir();
            new File(plugin.getDataFolder(), "html/script").mkdir();
            plugin.saveResource("html/assets/discord.svg", false);
            plugin.saveResource("html/assets/github.svg", false);
            plugin.saveResource("html/assets/world.svg", false);
            plugin.saveResource("html/script/index.js", false);
            plugin.saveResource("html/index.html", false);
            plugin.saveResource("html/style.css", false);
            Bukkit.getLogger().info("Site populated...");
            // Am I too lazy to make this dynamic? Yes
            // Will I ever change this site? Probably not
        }
    }
}
