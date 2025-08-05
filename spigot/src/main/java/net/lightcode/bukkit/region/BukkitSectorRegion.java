package net.lightcode.bukkit.region;

import net.lightcode.sector.region.SectorRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class BukkitSectorRegion {

    private final Vector minimumPoint, maximumPoint;

    private final Location centerLocation;

    public BukkitSectorRegion(SectorRegion region) {
        this.minimumPoint = new Vector(region.minX(), 0, region.minZ());
        this.maximumPoint = new Vector(region.maxX(), 256, region.maxZ());

        this.centerLocation = new Location(Bukkit.getWorld("world"), region.centerX(), 64, region.centerZ());
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

    public void setBounds(int minX, int maxX, int minZ, int maxZ) {
        this.minimumPoint.setX(minX);
        this.minimumPoint.setZ(minZ);
        this.maximumPoint.setX(maxX);
        this.maximumPoint.setZ(maxZ);

        int centerX = (minX + maxX) / 2;
        int centerZ = (minZ + maxZ) / 2;
        int centerY = 256;

        this.centerLocation.setX(centerX);
        this.centerLocation.setY(centerY);
        this.centerLocation.setZ(centerZ);
    }

    public boolean isInside(Location location) {
        Vector vector = location.toVector();
        return vector.isInAABB(this.minimumPoint, this.maximumPoint);
    }

}