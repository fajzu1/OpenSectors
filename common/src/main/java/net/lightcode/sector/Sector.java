package net.lightcode.sector;

import net.lightcode.sector.region.SectorRegion;
import net.lightcode.sector.type.SectorType;

import java.io.Serializable;
import java.util.Objects;

public class Sector implements Serializable {
    private final String id;

    private final SectorType sectorType;

    private final SectorRegion region;

    private int players;

    private long lastUpdate;

    private double tps;

    public Sector() {
        this.id = null;
        this.sectorType = null;
        this.region = null;
    }

    public Sector(String id, SectorType sectorType, int minX, int maxX, int minZ, int maxZ) {
        this.id = id;
        this.sectorType = sectorType;

        this.region = new SectorRegion(minX, maxX, minZ, maxZ);
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

    public int players() {
        return this.players;
    }

    public void players(int players) {
        this.players = players;
    }

    public double tps() {
        return this.tps;
    }

    public void tps(double tps) {
        this.tps = tps;
    }

    public long lastUpdate() {
        return this.lastUpdate;
    }

    public void lastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isOnline() {
        return this.lastUpdate + 7500L > System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Sector other = (Sector) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
