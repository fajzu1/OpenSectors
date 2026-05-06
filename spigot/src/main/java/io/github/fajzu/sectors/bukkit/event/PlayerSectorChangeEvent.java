package io.github.fajzu.sectors.bukkit.event;

import io.github.fajzu.shared.sector.Sector;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerSectorChangeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Sector currentSector, newSector;
    private final Player player;
    private boolean cancelled;

    public PlayerSectorChangeEvent(final @NotNull Player player,
                                   final @NotNull Sector currentSector,
                                   final @NotNull Sector newSector) {
        this.player = player;
        this.currentSector = currentSector;
        this.newSector = newSector;
        this.cancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
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
