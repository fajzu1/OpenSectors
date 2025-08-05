package net.lightcode.packet.impl;

import net.lightcode.packet.Packet;

public class PlayerConnectSectorPacket extends Packet {
    private final String name, sectorName;

    public PlayerConnectSectorPacket() {
        this(null, null);
    }

    public PlayerConnectSectorPacket(String name, String sectorName) {
        this.name = name;
        this.sectorName = sectorName;
    }

    public String name() {
        return this.name;
    }

    public String sectorName() {
        return this.sectorName;
    }
}
