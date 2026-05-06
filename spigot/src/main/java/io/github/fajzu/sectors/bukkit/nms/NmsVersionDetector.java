package io.github.fajzu.sectors.bukkit.nms;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class NmsVersionDetector {

    public String detect(final @NotNull Plugin plugin) {
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
}
