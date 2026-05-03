package io.github.fajzu.sectors.bukkit.inventory;

import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class Window {

    private final Inventory inventory;
    private final WindowHolder holder;

    public Window(final @NotNull String name,
                  final int rows) {
        this.holder = new WindowHolder();
        this.inventory = Bukkit.createInventory(this.holder, (rows > 6) ? 54 : (rows * 9), ChatHelper.colored(name));
        this.holder.setInventory(this.inventory);
    }

    public void setItem(final int slot,
                        final @NotNull ItemStack item,
                        final @NotNull Consumer<InventoryClickEvent> consumer) {
        this.holder.setActionOnSlot(slot, consumer);
        this.inventory.setItem(slot, item);
    }

    public void open(final @NotNull HumanEntity entity) {
        entity.openInventory(this.inventory);
    }
}
