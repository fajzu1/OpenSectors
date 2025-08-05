package net.lightcode.bukkit.helper;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ChatHelper {

    public static String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> colored(List<String> messages) {
        return messages.stream().map(ChatHelper::colored).collect(Collectors.toList());
    }

    public static String formatTps(double tps) {
        ChatColor color = ChatColor.RED;

        if (tps >= 19.0) {
            color = ChatColor.GREEN;
        } else if (tps >= 16.0) {
            color = ChatColor.YELLOW;
        }

        return color + String.format(Locale.US, "%.2f", Math.min(tps, 20.0));
    }

}
