package net.lightcode.sector.region;

import java.io.Serializable;

public class SectorRegion implements Serializable {
    private final int minX, maxX, minZ, maxZ;

    private final int centerX,centerZ;

    public SectorRegion() {
        this(0, 0, 0, 0);
    }

    public SectorRegion(int minX, int maxX, int minZ, int maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;

        this.centerX = (this.minX + this.maxX) / 2;
        this.centerZ = (this.minZ + this.maxZ) / 2;
    }

    public int minX() {
        return this.minX;
    }

    public int maxX() {
        return this.maxX;
    }

    public int minZ() {
        return this.minZ;
    }

    public int maxZ() {
        return this.maxZ;
    }

    public int centerX() {
        return this.centerX;
    }

    public int centerZ() {
        return this.centerZ;
    }

}
