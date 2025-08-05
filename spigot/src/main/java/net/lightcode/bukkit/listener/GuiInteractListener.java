package net.lightcode.bukkit.listener;

import net.lightcode.bukkit.inventory.api.holder.GuiHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiInteractListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();

        if (!inventory.getType().equals(InventoryType.CHEST)) return;

        final InventoryHolder inventoryHolder = inventory.getHolder();

        if (!(inventoryHolder instanceof GuiHolder)) return;

        final GuiHolder holder = (GuiHolder) inventoryHolder;

        event.setCancelled(true);

        holder.handleClick(event);
    }

    @EventHandler
    public void onInteract(InventoryInteractEvent event) {
        final Inventory inventory = event.getInventory();

        if (!inventory.getType().equals(InventoryType.CHEST)) return;

        if (!(inventory.getHolder() instanceof GuiHolder)) return;

        event.setCancelled(true);
    }
}
