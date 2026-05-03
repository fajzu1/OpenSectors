package io.github.fajzu.sectors.bukkit.inventory.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemBuilder {

    private final ItemStack itemStack;

    private ItemBuilder(final @NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    private ItemBuilder(final @NotNull Material material) {
        this.itemStack = new ItemStack(material);
    }

    private ItemBuilder(final @NotNull Material material,
                        final int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    private ItemBuilder(final @NotNull Material material,
                        final int amount,
                        final short data) {
        this.itemStack = new ItemStack(material, amount, data);
    }

    public static ItemBuilder from(final @NotNull Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder from(final @NotNull Material material,
                                   final int amount) {
        return new ItemBuilder(material, amount);
    }

    public static ItemBuilder from(final @NotNull Material material,
                                   final int amount,
                                   final short data) {
        return new ItemBuilder(material, amount, data);
    }

    public static ItemBuilder from(final @NotNull ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder title(String title) {
        if(title == null) {
            return this;
        }
        this.editMeta(itemMeta -> itemMeta.setDisplayName(title));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        if(lore == null) {
            return this;
        }

        this.editMeta(itemMeta -> itemMeta.setLore(lore));
        return this;
    }

    public ItemBuilder lore(final @NotNull String loreLine) {
        this.editMeta(itemMeta -> {
            List<String> lore = new ArrayList<>();

            if (itemMeta.hasLore()) {
                lore.addAll(itemMeta.getLore());
            }

            lore.add(loreLine);

            itemMeta.setLore(lore);
        });
        return this;
    }

    public ItemBuilder glow(final boolean glow) {
        this.editMeta(itemMeta -> {
            if (glow) {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        });
        return this;
    }

    public ItemBuilder type(final @NotNull Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder amount(final int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }


    public ItemBuilder itemFlags(final @NotNull List<ItemFlag> itemFlags) {
        this.editMeta(itemMeta -> {
            for (final ItemFlag itemFlag : itemFlags) {
                itemMeta.addItemFlags(itemFlag);
            }
        });
        return this;
    }

    public ItemBuilder enchantments(final @NotNull Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this.itemStack::addUnsafeEnchantment);
        return this;
    }

    public ItemBuilder skull(final @NotNull String name) {
        if (name.isEmpty()) {
            return this;
        }

        if (this.itemStack.getType() != Material.PLAYER_HEAD) {
            this.itemStack.setType(Material.PLAYER_HEAD);
        }

        final SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();

        skullMeta.setOwner(name);
        this.itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder texture(final @NotNull String texture) {
        if (this.itemStack.getType() != Material.PLAYER_HEAD) {
            this.itemStack.setType(Material.PLAYER_HEAD);
        }

        this.itemStack.setDurability((short) 3);

        final SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

        final GameProfile profile = new GameProfile(UUID.nameUUIDFromBytes(texture.getBytes()), "");
        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject GameProfile into SkullMeta", e);
        }

        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder unbreakable(final boolean unbreakable) {
        this.editMeta(itemMeta -> {
            this.itemStack.setDurability(this.itemStack.getType().getMaxDurability());

            if (unbreakable) {
                itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            }
        });
        return this;
    }

    public ItemBuilder itemFlag(final @NotNull ItemFlag itemFlag) {
        this.editMeta(itemMeta -> itemMeta.addItemFlags(itemFlag));
        return this;
    }

    private void editMeta(final @NotNull Consumer<ItemMeta> itemMetaConsumer) {
        final ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        itemMetaConsumer.accept(itemMeta);

        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemStack build() {
        return this.itemStack;
    }

}