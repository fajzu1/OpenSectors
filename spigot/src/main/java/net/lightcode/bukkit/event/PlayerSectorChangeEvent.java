package net.lightcode.bukkit.event;

import net.lightcode.sector.Sector;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSectorChangeEvent extends Event {

    private final HandlerList handlers = new HandlerList();

    private final boolean cancelled;

    private final Sector currentSector, newSector;

    private final Player player;

    public PlayerSectorChangeEvent(Player player, Sector currentSector, Sector newSector) {
        this.player = player;
        this.currentSector = currentSector;
        this.newSector = newSector;
        this.cancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public Sector currentSector() {
        return this.currentSector;
    }

    public Sector newSector() {
        return this.newSector;
    }

    public Player player() {
        return this.player;
    }
}
