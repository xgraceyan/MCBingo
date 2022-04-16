package me.grace.MCBingo;

import me.grace.MCBingo.Events.EventsClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    Commands commands = new Commands();
    public void onEnable() {
        getCommand(commands.bingo).setExecutor(commands);
        Bukkit.getServer().getPluginManager().registerEvents(new EventsClass(), this);
        Bukkit.getServer().getConsoleSender().sendMessage("Minecraft Bingo Enabled!");
    }
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage("Minecraft Bingo Disabled!");
    }

    public static String cc(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
