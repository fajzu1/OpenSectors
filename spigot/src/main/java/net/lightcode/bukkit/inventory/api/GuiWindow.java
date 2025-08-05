package net.lightcode.bukkit.inventory.api;

import net.lightcode.bukkit.helper.ChatHelper;
import net.lightcode.bukkit.inventory.api.holder.GuiHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class GuiWindow {
    private final Inventory inv;

    private final GuiHolder holder;

    public GuiWindow(String name, int rows) {
        this.holder = new GuiHolder();
        this.inv = Bukkit.createInventory(this.holder, (rows > 6) ? 54 : (rows * 9), ChatHelper.colored(name));
        this.holder.setInventory(this.inv);
    }

    public void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> consumer) {
        this.holder.setActionOnSlot(slot, consumer);
        this.inv.setItem(slot, item);
    }

    public void open(HumanEntity entity) {
        entity.openInventory(this.inv);
    }

}
