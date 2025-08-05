package net.lightcode.packet.impl;

import net.lightcode.packet.Packet;
import net.lightcode.sector.Sector;

public class SectorConfigurationResponsePacket extends Packet {

    private final Sector[] sectors;

    public SectorConfigurationResponsePacket() {
        this(null);
    }

    public SectorConfigurationResponsePacket(Sector[] sectors) {
        this.sectors = sectors;
    }

    public Sector[] sectors() {
        return this.sectors;
    }
}
