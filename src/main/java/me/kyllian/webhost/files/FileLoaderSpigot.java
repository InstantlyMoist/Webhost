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
            new File(plugin.getDataFolder(), "html/assets/css").mkdir();
            new File(plugin.getDataFolder(), "html/assets/img").mkdir();
            new File(plugin.getDataFolder(), "html/assets/js").mkdir();

            plugin.saveResource("html/assets/css/style.css", false);
            plugin.saveResource("html/assets/img/background.png", false);
            plugin.saveResource("html/assets/js/main.js", false);
            plugin.saveResource("html/index.html", false);
            Bukkit.getLogger().info("Site populated...");
            // Am I too lazy to make this dynamic? Yes
            // Will I ever change this site? Probably not
        }
    }
}
