package me.kyllian.webhost.commands;

import me.kyllian.webhost.WebhostPluginSpigot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WebhostExecutor implements CommandExecutor {

    private WebhostPluginSpigot plugin;

    public WebhostExecutor(WebhostPluginSpigot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] strings) {
        sender.sendMessage(colorTranslate("&7WebHost is made by Kyllian"));
        sender.sendMessage("");
        sender.sendMessage(colorTranslate("&7The server appears to be running on <your ip>:" + plugin.getConfig().getInt("port")));
        sender.sendMessage(colorTranslate("Need help? https://discord.gg/zgKr2YM"));
        return true;
    }

    private String colorTranslate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
