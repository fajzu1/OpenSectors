package io.github.fajzu.shared.network.packet;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@packetType")
public class Packet implements Serializable {

    private String sender;

    public Packet() {
    }

    public String sender() {
        return this.sender;
    }

    public void sender(final String sender) {
        this.sender = sender;
    }
}
