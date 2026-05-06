package io.github.fajzu.shared.network.packet.internal;

import io.github.fajzu.shared.network.packet.Packet;

public class PlayerTransferRequestPacket extends Packet {

    private final String name;

    public PlayerTransferRequestPacket() {
        this(null);
    }

    public PlayerTransferRequestPacket(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }
}
