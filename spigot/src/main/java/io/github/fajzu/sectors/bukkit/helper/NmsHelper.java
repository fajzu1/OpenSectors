package io.github.fajzu.sectors.bukkit.helper;

import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class NmsHelper {

    public static NmsService findNmsService(final Plugin plugin) {
        final String nmsVersion = findBukkitVersion(plugin);
        final String className = String.format("io.github.fajzu.nms.%s.%sNmsService", nmsVersion, nmsVersion);

        if (isAtLeast117(nmsVersion) && !isPaperLike()) {
            plugin.logger().severe("==================================================");
            plugin.logger().severe(" ");
            plugin.logger().severe("   ERROR: This plugin requires Paper (or a fork) ");
            plugin.logger().severe("   when running Minecraft version 1.17 or higher! ");
            plugin.logger().severe(" ");
            plugin.logger().severe("   Detected server: " + plugin.getServer().getName());
            plugin.logger().severe("   Detected NMS:    " + nmsVersion);
            plugin.logger().severe(" ");
            plugin.logger().severe("   Please switch to Paper, Purpur, or Pufferfish.");
            plugin.logger().severe(" ");
            plugin.logger().severe("==================================================");

            plugin.getServer().shutdown();
            throw new IllegalStateException("Unsupported server engine for " + nmsVersion);
        }

        try {
            return (NmsService) Class.forName(className).newInstance();
        } catch (final Exception exception) {
            throw new RuntimeException("Not found nms for version " + nmsVersion);
        }
    }

    protected static String findBukkitVersion(final Plugin plugin) {
        final String packageName = plugin.getServer().getClass().getPackage().getName();
        final String[] parts = packageName.split("\\.");

        if (parts.length > 3) {
            return parts[3];
        }

        final String version = plugin.getServer().getBukkitVersion();
        final String clean = version.split("-")[0];
        final String[] nums = clean.split("\\.");

        return "v" + nums[0] + "_" + nums[1] + "R1";
    }

    private static boolean isAtLeast117(final String nmsVersion) {
        try {
            final String[] parts = nmsVersion.substring(1).split("_");
            final int major = Integer.parseInt(parts[0]);
            final int minor = Integer.parseInt(parts[1]);
            return (major > 1) || (major == 1 && minor >= 17);
        } catch (final Exception e) {
            return false;
        }
    }

    private static boolean isPaperLike() {
        final String name = Bukkit.getServer().getName();

        if (name.equalsIgnoreCase("Paper") || name.equalsIgnoreCase("Purpur") || name.equalsIgnoreCase("Pufferfish")) {
            return true;
        }

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (final ClassNotFoundException ignored) {
        }

        try {
            Class.forName("io.papermc.paper.configuration.Configuration");
            return true;
        } catch (final ClassNotFoundException ignored) {
        }

        return false;
    }
}
