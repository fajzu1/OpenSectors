package io.github.fajzu.sectors.bukkit.region;

import io.github.fajzu.shared.sector.SectorRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public final class BukkitSectorRegion {

    private final Vector minimumPoint, maximumPoint;
    private final Location centerLocation;

    public BukkitSectorRegion(final SectorRegion region) {
        final World world = Bukkit.getWorld("world");

        this.minimumPoint = new Vector(region.minX(), 0, region.minZ());
        this.maximumPoint = new Vector(region.maxX(), world.getMaxHeight(), region.maxZ());

        this.centerLocation = new Location(world, region.centerX(), 64, region.centerZ());
    }

    public Vector minimumPoint() {
        return this.minimumPoint;
    }

    public Vector maximumPoint() {
        return this.maximumPoint;
    }

    public Location center() {
        return this.centerLocation;
    }

    public boolean isInside(final @NotNull Location location) {
        final Vector vector = location.toVector();
        return vector.isInAABB(this.minimumPoint, this.maximumPoint);
    }
}
