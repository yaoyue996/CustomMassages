package com.example.custommessages;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomMessagesPlugin extends JavaPlugin {

    private static CustomMessagesPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getLogger().info("CustomMessages has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomMessages has been disabled.");
    }

    public static CustomMessagesPlugin getInstance() {
        return instance;
    }

    public String formatMessage(String path, String... replacements) {
        String message = getConfig().getString(path, "");
        if (message == null || message.isEmpty()) {
            return null;
        }
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
