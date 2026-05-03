package io.github.fajzu.sectors.bukkit.region;

import com.google.inject.Inject;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BukkitSectorRegionService {

    private final Map<String, BukkitSectorRegion> sectorRegions = new ConcurrentHashMap<>();
    private final BukkitSectorPluginController plugin;

    @Inject
    public BukkitSectorRegionService(final BukkitSectorPluginController plugin) {
        this.plugin = plugin;
    }

    public void create(final String name,
                       final BukkitSectorRegion bukkitSectorRegion) {
        this.sectorRegions.put(name, bukkitSectorRegion);
    }

    public BukkitSectorRegion find(final String name) {
        return this.sectorRegions.get(name);
    }

    public Sector find(final @NotNull Location location) {
        return this.plugin.sectorService().sectors().values().stream()
                .filter(sector -> {
                    final BukkitSectorRegion region = this.sectorRegions.get(sector.id());
                    return region != null && region.isInside(location);
                })
                .filter(sector -> !sector.equals(this.plugin.sectorService().currentSector()))
                .findFirst()
                .orElse(null);
    }

    public Location randomLocation(final @NotNull Sector sector) {
        final double x = sector.region().minX() + Math.random() * (sector.region().maxX() - sector.region().minX());
        final double z = sector.region().minZ() + Math.random() * (sector.region().maxZ() - sector.region().minZ());

        final World world = Bukkit.getWorld("world");

        return new Location(world, x, world.getHighestBlockYAt((int) x, (int) z), z);
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

    public Map<String, BukkitSectorRegion> regions() {
        return this.sectorRegions;
    }

    public BukkitSectorRegion currentSectorRegion() {
        return this.sectorRegions.get(this.plugin.sectorService().currentSectorId());
    }
}
