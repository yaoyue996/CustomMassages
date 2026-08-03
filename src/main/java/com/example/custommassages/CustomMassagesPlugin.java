package com.example.custommessages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomMessagesPlugin extends JavaPlugin {

    private static final LegacyComponentSerializer SERIALIZER =
            LegacyComponentSerializer.legacyAmpersand();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getLogger().info("CustomMessages has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("CustomMessages has been disabled.");
    }

    public Component formatMessage(String path, String... replacements) {
        String message = getConfig().getString(path, "");
        if (message == null || message.isEmpty()) {
            return null;
        }
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }
        return SERIALIZER.deserialize(message);
    }
}
