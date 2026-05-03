package io.github.fajzu.sectors.bukkit.event;

import io.github.fajzu.sectors.bukkit.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerSaveDataEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Profile profile;
    private boolean cancelled;

    public PlayerSaveDataEvent(final @NotNull Player player,
                               final @NotNull Profile profile) {
        this.cancelled = false;
        this.player = player;
        this.profile = profile;
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

    public Player player() {
        return this.player;
    }

    public Profile profile() {
        return this.profile;
    }
}
