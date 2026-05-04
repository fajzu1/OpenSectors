package io.github.fajzu.sectors.bukkit.nms;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class NmsPlatformValidator {

    public void validate(final @NotNull Plugin plugin,
                         final @NotNull String version) {
        if (this.isAtLeast117(version) && !this.isPaperLike()) {
            plugin.getLogger().severe("==================================================");
            plugin.getLogger().severe(" ");
            plugin.getLogger().severe("   ERROR: This plugin requires Paper (or a fork) ");
            plugin.getLogger().severe("   when running Minecraft version 1.17 or higher! ");
            plugin.getLogger().severe(" ");
            plugin.getLogger().severe("   Detected server: " + plugin.getServer().getName());
            plugin.getLogger().severe("   Detected NMS:    " + version);
            plugin.getLogger().severe(" ");
            plugin.getLogger().severe("   Please switch to Paper, Purpur, or Pufferfish.");
            plugin.getLogger().severe(" ");
            plugin.getLogger().severe("==================================================");

            plugin.getServer().shutdown();
            throw new IllegalStateException("Unsupported server engine for " + version);
        }
    }

    private boolean isAtLeast117(final @NotNull String version) {
        try {
            final String[] parts = version.substring(1).split("_");
            final int major = Integer.parseInt(parts[0]);
            final int minor = Integer.parseInt(parts[1]);
            return (major > 1) || (major == 1 && minor >= 17);
        } catch (final Exception e) {
            return false;
        }
    }

    private boolean isPaperLike() {
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
