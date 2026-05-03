package io.github.fajzu.shared.sector;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Sector implements Serializable {
    private final String id;

    private final SectorType sectorType;

    private final SectorRegion region;
    private final SectorStatistics statistics;

    public Sector() {
        this.id = null;
        this.sectorType = null;
        this.region = null;
        this.statistics = null;
    }

    public Sector(final @NotNull String id,
                  final @NotNull SectorType sectorType,
                  final int minX,
                  final int maxX,
                  final int minZ,
                  final int maxZ) {
        this.id = id;
        this.sectorType = sectorType;

        this.region = new SectorRegion(minX, maxX, minZ, maxZ);
        this.statistics = new SectorStatistics();
    }

    public SectorStatistics statistics() {
        return this.statistics;
    }

    public SectorRegion region() {
        return this.region;
    }

    public String id() {
        return this.id;
    }

    public SectorType sectorType() {
        return this.sectorType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Sector other = (Sector) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
