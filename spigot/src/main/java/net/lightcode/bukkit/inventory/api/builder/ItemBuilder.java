package net.lightcode.bukkit.inventory.api.builder;

import net.lightcode.bukkit.helper.ChatHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material m, int amount, short durability) {
        itemStack = new ItemStack(m, amount, durability);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(this.itemStack.clone());
    }

    public ItemBuilder durability(short dur) {
        this.itemStack.setDurability(dur);
        return this;
    }

    public ItemBuilder unsafeEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder enchantments(Map<Enchantment, Integer> enchantments) {
        this.itemStack.addEnchantments(enchantments);
        return this;
    }


    public ItemBuilder removeLoreLine(String line) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null || itemMeta.getLore() == null) return this;

        List<String> lore = itemMeta.getLore().stream()
                .filter(l -> !l.equals(line))
                .collect(Collectors.toList());

        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeLore(int index) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null || itemMeta.getLore() == null) return this;

        List<String> lore = new ArrayList<>(itemMeta.getLore());

        if (index < 0 || index >= lore.size()) return this;

        lore.remove(index);

        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(String line) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null) return this;

        List<String> lore = Optional.ofNullable(itemMeta.getLore())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);

        lore.add(ChatHelper.colored(line));
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lores(List<String> lines) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null) return this;

        List<String> lore = Optional.ofNullable(itemMeta.getLore())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);

        lore.addAll(lines.stream()
                .map(ChatHelper::colored)
                .collect(Collectors.toList()));

        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(String line, int pos) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null) return this;

        List<String> lore = Optional.ofNullable(itemMeta.getLore())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);

        if (pos >= 0 && pos < lore.size()) {
            lore.set(pos, ChatHelper.colored(line));
        } else {
            lore.add(ChatHelper.colored(line));
        }

        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public List<String> lore() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        return itemMeta != null ? itemMeta.getLore() : Collections.emptyList();
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null) return this;
        
        List<String> coloredLore = lore.stream()
                .map(ChatHelper::colored)
                .collect(Collectors.toList());

        itemMeta.setLore(coloredLore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public String name() {
        return this.itemStack.getItemMeta().getDisplayName();
    }

    public ItemBuilder name(String name) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatHelper.colored(name));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder glow(boolean glow) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (glow) {
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            this.itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public Material type() {
        return this.itemStack.getType();
    }

    public ItemBuilder type(Material m) {
        this.itemStack.setType(m);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(flag);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
}