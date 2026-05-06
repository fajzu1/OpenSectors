package io.github.fajzu.sectors.bukkit.region;

import com.google.inject.Inject;
import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.shared.Schedule;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@Schedule(period = 50L)
public class SectorBorderUpdateRunnable extends BukkitRunnable {

    private final BukkitSectorRegionService regionService;
    private final Plugin plugin;
    private final NmsService nmsService;

    @Inject
    public SectorBorderUpdateRunnable(final @NotNull BukkitSectorRegionService regionService,
                                      final @NotNull Plugin plugin,
                                      final @NotNull NmsService nmsService) {
        this.regionService = regionService;
        this.plugin = plugin;
        this.nmsService = nmsService;
    }

    @Override
    public void run() {
        final BukkitSectorRegion sector = this.regionService.currentSectorRegion();

        final double minX = sector.minimumPoint().getX();
        final double minZ = sector.minimumPoint().getZ();
        final double maxX = sector.maximumPoint().getX();
        final double maxZ = sector.maximumPoint().getZ();

        final double xDiff = Math.abs(maxX - minX) + 1;
        final double zDiff = Math.abs(maxZ - minZ) + 1;

        final double centerX = (minX + maxX) / 2.0;
        final double centerZ = (minZ + maxZ) / 2.0;

        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            final Location location = player.getLocation();

            final double addX = xDiff > zDiff ? location.getX() - centerX : 0;
            final double addZ = zDiff > xDiff ? location.getZ() - centerZ : 0;

            this.nmsService.border().sendWorldBorder(player, Math.min(xDiff, zDiff) + 0.4, centerX + 0.5 + addX, centerZ + 0.5 + addZ);
        }
    }
}
