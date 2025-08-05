package net.lightcode.packet.impl;

import net.lightcode.packet.Packet;

public class SectorInformationUpdatePacket extends Packet {

    private final int players;
    private final double tps;

    public SectorInformationUpdatePacket() {
        this(0, 0);
    }

    public SectorInformationUpdatePacket(int players, double tps) {
        this.players = players;
        this.tps = tps;
    }

    public int players() {
        return this.players;
    }

    public double tps() {
        return this.tps;
    }
}
