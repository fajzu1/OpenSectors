package net.lightcode.bukkit.inventory.api.holder;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class GuiHolder implements InventoryHolder {
    private final Map<Integer, Consumer<InventoryClickEvent>> actions;

    private Inventory inventory;

    public GuiHolder() {
        this.actions = new ConcurrentHashMap<>();
    }

    public void handleClick(InventoryClickEvent event) {
        this.actions.getOrDefault(event.getRawSlot(), e -> e.setCancelled(true)).accept(event);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setActionOnSlot(int slot, Consumer<InventoryClickEvent> consumer) {
        this.actions.put(slot, (consumer != null) ? consumer : (event -> {
        }));
    }
}
