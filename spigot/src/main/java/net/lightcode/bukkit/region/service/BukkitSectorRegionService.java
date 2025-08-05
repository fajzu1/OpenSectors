package net.lightcode.bukkit.region.service;

import net.lightcode.bukkit.region.BukkitSectorRegion;
import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.sector.Sector;
import net.lightcode.sector.type.SectorType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class BukkitSectorRegionService {

    private final Map<String, BukkitSectorRegion> sectorRegions = new ConcurrentHashMap<>();

    private final BukkitSectorPlugin plugin;

    public BukkitSectorRegionService(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    public void create(String name, BukkitSectorRegion bukkitSectorRegion) {
        this.sectorRegions.put(name, bukkitSectorRegion);
    }

    public Optional<BukkitSectorRegion> find(String name) {
        return Optional.ofNullable(this.sectorRegions.get(name));
    }

    public Optional<Sector> find(Location location) {
        return this.plugin.sectorService().sectors().values().stream().filter(sector -> {
            BukkitSectorRegion region = this.sectorRegions.get(sector.id());

            return region != null && region.isInside(location);
        }).filter(sector -> !sector.equals(this.plugin.sectorService().currentSector()))
                .filter(sector -> !sector.sectorType().equals(SectorType.SPAWN) || !this.plugin.sectorService().currentSector().sectorType().equals(SectorType.SPAWN)).findFirst();
    }


    public void knock(Player player) {
        final BukkitSectorRegion sectorRegion = this.currentSectorRegion();

        if(sectorRegion == null) return;

        final Location location = new Location(player.getLocation().getWorld(),
                sectorRegion.center().getX(),
                player.getLocation().getY(),
                sectorRegion.center().getZ());

        player.setVelocity(location.toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.2D));

    }

    public double distance(Location location) {
        final BukkitSectorRegion sectorRegion = this.currentSectorRegion();

        if (sectorRegion == null) return 0.0;

        final Vector point = location.toVector();

        return Math.min(
                Math.min(Math.abs(point.getX() - sectorRegion.minimumPoint().getX()), Math.abs(sectorRegion.maximumPoint().getX() - point.getX())),
                Math.min(Math.abs(point.getZ() - sectorRegion.maximumPoint().getZ()), Math.abs(sectorRegion.maximumPoint().getZ() - point.getZ()))
        );
    }

    public Map<String, BukkitSectorRegion> sectorRegions() {
        return this.sectorRegions;
    }

    public BukkitSectorRegion currentSectorRegion() {
        return this.sectorRegions.get(this.plugin.sectorService().currentSectorId());
    }
}
