package io.github.fajzu.shared.sector;

public final class SectorStatistics {

    private int players;

    private long lastUpdate;

    private double tps;

    public boolean isOnline() {
        return this.lastUpdate + 7500L > System.currentTimeMillis();
    }

    public int players() {
        return this.players;
    }

    public void players(final int players) {
        this.players = players;
    }

    public double tps() {
        return this.tps;
    }

    public void tps(final double tps) {
        this.tps = tps;
    }

    public long lastUpdate() {
        return this.lastUpdate;
    }

    public void lastUpdate(final long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
