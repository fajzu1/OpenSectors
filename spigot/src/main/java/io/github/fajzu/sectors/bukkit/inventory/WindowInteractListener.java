package io.github.fajzu.sectors.bukkit.inventory;

import io.github.fajzu.shared.sector.SectorInitialize;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

@SectorInitialize
public class WindowInteractListener implements Listener {

    @EventHandler
    void onInventoryClick(final InventoryClickEvent event) {
        if (!this.isGuiWindow(event.getInventory())) {
            return;
        }

        final WindowHolder holder = (WindowHolder) event.getInventory().getHolder();
        event.setCancelled(true);

        holder.handleClick(event);
    }

    @EventHandler
    void onInventoryInteract(final InventoryInteractEvent event) {
        if (!this.isGuiWindow(event.getInventory())) {
            return;
        }

        event.setCancelled(true);
    }

    private boolean isGuiWindow(final @NotNull Inventory inventory) {
        return inventory != null
                && inventory.getType() == InventoryType.CHEST
                && inventory.getHolder() instanceof WindowHolder;
    }
}
