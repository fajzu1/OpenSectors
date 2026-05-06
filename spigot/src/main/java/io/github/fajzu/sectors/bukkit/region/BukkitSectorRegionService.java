package io.github.fajzu.sectors.bukkit.region;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.shared.sector.SectorService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class BukkitSectorRegionService {

    private final SectorService sectorService;
    private final BukkitSectorRegionCache regionCache;

    @Inject
    public BukkitSectorRegionService(final @NotNull SectorService sectorService,
                                     final @NotNull BukkitSectorRegionCache regionCache) {
        this.sectorService = sectorService;
        this.regionCache = regionCache;
    }

    public Sector find(final @NotNull Location location) {
        return this.sectorService.sectors().values().stream()
                .filter(sector -> {
                    final BukkitSectorRegion region = this.regionCache.regions().get(sector.id());
                    return region != null && region.isInside(location);
                })
                .filter(sector -> !sector.equals(this.sectorService.currentSector()))
                .findFirst()
                .orElse(null);
    }

    public Location randomLocation(final @NotNull Sector sector) {
        final double x = sector.region().minX() + Math.random() * (sector.region().maxX() - sector.region().minX());
        final double z = sector.region().minZ() + Math.random() * (sector.region().maxZ() - sector.region().minZ());

        final World world = Bukkit.getWorld("world");

        return new Location(world, x, world.getHighestBlockYAt((int) x, (int) z), z);
    }

    public boolean isOutsideBorder(final @NotNull Location location) {
        final BukkitSectorRegion region = this.currentSectorRegion();
        if (region == null) {
            return false;
        }

        final double OFFSET = 0.5;

        final double x = location.getX();
        final double z = location.getZ();

        return x < (region.minimumPoint().getX() + OFFSET)
            || x > (region.maximumPoint().getX() - OFFSET)
            || z < (region.minimumPoint().getZ() + OFFSET)
            || z > (region.maximumPoint().getZ() - OFFSET);
    }

    public boolean isNearBorder(
        final @NotNull Location location,
        final double radius) {
        final BukkitSectorRegion region = this.currentSectorRegion();
        if (region == null) {
            return false;
        }

        final double x = location.getX();
        final double z = location.getZ();

        return x - region.minimumPoint().getX() <= radius ||
            region.maximumPoint().getX() - x <= radius ||
            z - region.minimumPoint().getZ() <= radius ||
            region.maximumPoint().getZ() - z <= radius;
    }

    public void knock(final @NotNull Player player) {
        final BukkitSectorRegion sectorRegion = this.currentSectorRegion();

        if (sectorRegion == null) {
            return;
        }

        final Location location = new Location(player.getLocation().getWorld(),
                sectorRegion.center().getX(),
                player.getLocation().getY(),
                sectorRegion.center().getZ());

        player.setVelocity(location.toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.2D));
    }

    public double distance(final @NotNull Location location) {
        final BukkitSectorRegion sectorRegion = this.currentSectorRegion();

        if (sectorRegion == null) {
            return 0.0;
        }

        final Vector point = location.toVector();

        return Math.min(
                Math.min(Math.abs(point.getX() - sectorRegion.minimumPoint().getX()), Math.abs(sectorRegion.maximumPoint().getX() - point.getX())),
                Math.min(Math.abs(point.getZ() - sectorRegion.maximumPoint().getZ()), Math.abs(sectorRegion.maximumPoint().getZ() - point.getZ()))
        );
    }

    public BukkitSectorRegion currentSectorRegion() {
        return this.regionCache.regions().get(this.sectorService.currentSectorId());
    }
}
