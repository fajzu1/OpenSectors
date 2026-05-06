package io.github.fajzu.sectors.bukkit.helper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ChatHelper {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static Component colored(final @NotNull String message) {
        return MINI_MESSAGE.deserialize(message);
    }

    public static List<Component> colored(final @NotNull List<String> messages) {
        return messages.stream()
                .map(ChatHelper::colored)
                .collect(Collectors.toList());
    }

    public static String formatTps(final double tps) {
        ChatColor color = ChatColor.RED;

        if (tps >= 19.0) {
            color = ChatColor.GREEN;
        } else if (tps >= 16.0) {
            color = ChatColor.YELLOW;
        }

        return color + String.format(Locale.US, "%.2f", Math.min(tps, 20.0));
    }
}
