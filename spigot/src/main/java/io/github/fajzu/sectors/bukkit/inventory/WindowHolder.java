package io.github.fajzu.sectors.bukkit.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class WindowHolder implements InventoryHolder {

    private final Map<Integer, Consumer<InventoryClickEvent>> actions;
    private Inventory inventory;

    public WindowHolder() {
        this.actions = new ConcurrentHashMap<>();
    }

    public void handleClick(final InventoryClickEvent event) {
        this.actions.getOrDefault(event.getRawSlot(), e -> e.setCancelled(true)).accept(event);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(final Inventory inventory) {
        this.inventory = inventory;
    }

    public void setActionOnSlot(final int slot,
                                final Consumer<InventoryClickEvent> consumer) {
        this.actions.put(slot, (consumer != null) ? consumer : (event -> {
        }));
    }
}
