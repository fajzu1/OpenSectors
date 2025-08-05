package net.lightcode.packet;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class Packet implements Serializable {

    private String sender;

    public Packet() {
    }

    public String sender() {
        return this.sender;
    }

    public void sender(String sender) {
        this.sender = sender;
    }
}