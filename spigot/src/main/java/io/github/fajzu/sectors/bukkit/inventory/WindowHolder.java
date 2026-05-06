package io.github.fajzu.sectors.bukkit.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class WindowHolder implements InventoryHolder {

    private final Map<Integer, Consumer<InventoryClickEvent>> actions = new ConcurrentHashMap<>();

    private Inventory inventory;

    public void handleClick(final @NotNull InventoryClickEvent event) {
        this.actions.getOrDefault(event.getRawSlot(), inventoryClickEvent -> {}).accept(event);
    }

    @Override
    public @NonNull Inventory getInventory() {
        return this.inventory;
    }

    public void inventory(final @NotNull Inventory inventory) {
        this.inventory = inventory;
    }

    public void actionOnSlot(final int slot,
                             final @NotNull Consumer<InventoryClickEvent> consumer) {
        this.actions.put(slot, consumer);
    }
}
