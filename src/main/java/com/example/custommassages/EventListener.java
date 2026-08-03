package com.example.custommessages;

import io.papermc.paper.advancement.AdvancementDisplay;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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
        Component formatted = plugin.formatMessage("join-message",
                "{player}", event.getPlayer().getName());
        if (formatted != null) {
            event.joinMessage(formatted);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Component formatted = plugin.formatMessage("quit-message",
                "{player}", event.getPlayer().getName());
        if (formatted != null) {
            event.quitMessage(formatted);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        String cause = extractCause(event);
        Component formatted = plugin.formatMessage("death-message",
                "{player}", event.getPlayer().getName(),
                "{message}", cause);
        if (formatted != null) {
            event.deathMessage(formatted);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        AdvancementDisplay display = event.getAdvancement().getDisplay();
        if (display == null || !display.doesAnnounceToChat()) {
            return;
        }
        String advName = PlainTextComponentSerializer.plainText()
                .serialize(display.title());
        Component formatted = plugin.formatMessage("advancement-message",
                "{player}", event.getPlayer().getName(),
                "{advancement}", advName);
        if (formatted != null) {
            Bukkit.broadcast(formatted);
        }
    }

    private String extractCause(PlayerDeathEvent event) {
        Component deathMessage = event.deathMessage();
        if (deathMessage == null) {
            return "died";
        }
        String text = PlainTextComponentSerializer.plainText().serialize(deathMessage);
        if (text.isEmpty()) {
            return "died";
        }
        Player player = event.getPlayer();
        String name = player.getName();
        if (text.startsWith(name)) {
            return text.substring(name.length()).trim();
        }
        String displayName = PlainTextComponentSerializer.plainText()
                .serialize(player.displayName());
        if (text.startsWith(displayName)) {
            return text.substring(displayName.length()).trim();
        }
        return text;
    }
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
