package net.lightcode.packet.impl;

import net.lightcode.packet.Packet;

public class UserSynchronizeDataPacket extends Packet {

    private final String serializedUser, sectorName;

    public UserSynchronizeDataPacket() {
        this(null, null);
    }

    public UserSynchronizeDataPacket(String serializedUser, String sectorName) {
        this.serializedUser = serializedUser;
        this.sectorName = sectorName;
    }

    public String sectorName() {
        return this.sectorName;
    }

    public String serializedUser() {
        return this.serializedUser;
    }
}
