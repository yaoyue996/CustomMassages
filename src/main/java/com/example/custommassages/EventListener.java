package com.example.custommessages;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    private final CustomMessagesPlugin plugin;

    public EventListener(CustomMessagesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        String formatted = plugin.formatMessage("join-message",
                "{player}", event.getPlayer().getName());
        if (formatted != null) {
            event.setJoinMessage(formatted);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        String formatted = plugin.formatMessage("quit-message",
                "{player}", event.getPlayer().getName());
        if (formatted != null) {
            event.setQuitMessage(formatted);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        String cause = extractCause(event);
        String formatted = plugin.formatMessage("death-message",
                "{player}", event.getEntity().getName(),
                "{message}", cause);
        if (formatted != null) {
            event.setDeathMessage(formatted);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        String key = event.getAdvancement().getKey().getKey();
        if (key.startsWith("recipes/") || key.endsWith("/root")) {
            return;
        }
        String advName = formatKeyName(key);
        String formatted = plugin.formatMessage("advancement-message",
                "{player}", event.getPlayer().getName(),
                "{advancement}", advName);
        if (formatted != null) {
            Bukkit.broadcastMessage(formatted);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAchievementAwarded(PlayerAchievementAwardedEvent event) {
        String achName = formatAchievementName(event.getAchievement());
        String formatted = plugin.formatMessage("advancement-message",
                "{player}", event.getPlayer().getName(),
                "{advancement}", achName);
        if (formatted != null) {
            Bukkit.broadcastMessage(formatted);
        }
    }

    private String extractCause(PlayerDeathEvent event) {
        String deathMessage = event.getDeathMessage();
        if (deathMessage == null || deathMessage.isEmpty()) {
            return "died";
        }
        Player player = event.getEntity();
        String name = player.getName();
        if (deathMessage.startsWith(name)) {
            return deathMessage.substring(name.length()).trim();
        }
        String displayName = player.getDisplayName();
        if (deathMessage.startsWith(displayName)) {
            return deathMessage.substring(displayName.length()).trim();
        }
        return deathMessage;
    }

    private String formatKeyName(String key) {
        String name = key.substring(key.lastIndexOf('/') + 1).replace('_', ' ');
        if (name.isEmpty()) {
            return key;
        }
        return capitalizeWords(name);
    }

    private String formatAchievementName(Achievement achievement) {
        String name = achievement.name().replace('_', ' ').toLowerCase();
        return capitalizeWords(name);
    }

    private String capitalizeWords(String text) {
        String[] words = text.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() > 1) {
                sb.append(word.substring(1));
            }
        }
        return sb.toString();
    }
}
