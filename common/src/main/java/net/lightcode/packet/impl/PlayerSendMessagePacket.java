package net.lightcode.packet.impl;

import net.lightcode.packet.Packet;

public class PlayerSendMessagePacket extends Packet {

    private final String name;

    private final String message;

    public PlayerSendMessagePacket() {
        this(null,null);
    }

    public PlayerSendMessagePacket(String name,String message) {
        this.name = name;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public String name() {
        return this.name;
    }
}
