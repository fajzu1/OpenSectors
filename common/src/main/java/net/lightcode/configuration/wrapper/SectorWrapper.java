package net.lightcode.configuration.wrapper;

import net.lightcode.sector.type.SectorType;

public class SectorWrapper {

    private final String name;

    private final SectorType sectorType;

    private final int minX, maxX, minZ, maxZ;

    public SectorWrapper(String name, SectorType sectorType, int minX, int maxX, int minZ, int maxZ) {
        this.name = name;
        this.sectorType = sectorType;
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    public String name() {
        return this.name;
    }

    public SectorType sectorType() {
        return this.sectorType;
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
}
