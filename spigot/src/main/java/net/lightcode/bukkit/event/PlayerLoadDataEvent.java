package net.lightcode.bukkit.event;

import net.lightcode.bukkit.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoadDataEvent extends Event {
    private final HandlerList handlers = new HandlerList();

    private final boolean cancelled;

    private final Player player;

    private final User user;

    public PlayerLoadDataEvent(Player player, User user) {
        this.cancelled = false;
        this.player = player;
        this.user = user;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public Player player() {
        return this.player;
    }

    public User user() {
        return this.user;
    }
}
