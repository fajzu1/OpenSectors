package io.github.fajzu.sectors.bukkit.region;

import io.github.fajzu.shared.sector.SectorRegion;

public final class BukkitSectorRegionFactory {
    public static BukkitSectorRegion create(final SectorRegion region) {
        return new BukkitSectorRegion(region);
    }
}
