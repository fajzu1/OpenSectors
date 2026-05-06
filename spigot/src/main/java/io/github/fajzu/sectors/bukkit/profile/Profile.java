package io.github.fajzu.sectors.bukkit.profile;

import java.util.UUID;

public class Profile {

    private final String name;
    private final UUID uuid;

    private String serializedData, gameMode;
    private byte[] serializedLocation;
    private int heldSlot;
    private long redirectTime, transferCooldown;

    public Profile(final String name,
                   final UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public boolean isRedirecting() {
        return this.redirectTime + 5000L > System.currentTimeMillis();
    }

    public void redirecting(final boolean redirecting) {
        this.redirectTime = redirecting ? System.currentTimeMillis() : 0L;
    }

    public boolean isTransferCooldown() {
        return this.transferCooldown + 5000L > System.currentTimeMillis();
    }

    public void transferCooldown(final boolean transfer) {
        this.transferCooldown = transfer ? System.currentTimeMillis() : 0L;
    }

    public String name() {
        return this.name;
    }

    public UUID uuid() {
        return this.uuid;
    }

    public String serializedData() {
        return this.serializedData;
    }

    public Profile serializedData(final String serializedData) {
        this.serializedData = serializedData;
        return this;
    }

    public String gameMode() {
        return this.gameMode;
    }

    public Profile gameMode(final String gameMode) {
        this.gameMode = gameMode;
        return this;
    }

    public byte[] serializedLocation() {
        return this.serializedLocation;
    }

    public Profile serializedLocation(final byte[] serializedLocation) {
        this.serializedLocation = serializedLocation;
        return this;
    }

    public int heldSlot() {
        return this.heldSlot;
    }

    public Profile heldSlot(final int heldSlot) {
        this.heldSlot = heldSlot;
        return this;
    }

    public long redirectTime() {
        return this.redirectTime;
    }

    public Profile redirectTime(final long redirectTime) {
        this.redirectTime = redirectTime;
        return this;
    }

    public long transferCooldown() {
        return this.transferCooldown;
    }

    public Profile transferCooldown(final long transferCooldown) {
        this.transferCooldown = transferCooldown;
        return this;
    }
}
