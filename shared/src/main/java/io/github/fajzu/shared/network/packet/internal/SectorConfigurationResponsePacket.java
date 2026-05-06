package io.github.fajzu.shared.network.packet.internal;

import io.github.fajzu.shared.network.packet.Packet;
import io.github.fajzu.shared.sector.Sector;

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
