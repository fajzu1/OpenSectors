package net.lightcode.v1_16R3.data;

import net.lightcode.data.Data;
import org.bukkit.entity.Player;

public class v1_16R3Data implements Data<Player, Object> {
    @Override
    public String saveData(Player player) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer");
            Object entityPlayer = craftPlayerClass.getMethod("getHandle").invoke(player);

            Class<?> nbtClass = Class.forName("net.minecraft.server.v1_16_R3.NBTTagCompound");
            Object nbtTag = nbtClass.getConstructor().newInstance();

            entityPlayer.getClass().getMethod("save", nbtClass).invoke(entityPlayer, nbtTag);
            return nbtTag.toString();
        } catch (Exception exception) {
            throw new RuntimeException("Failed to save player data", exception);
        }
    }

    @Override
    public void loadData(Player player, Object nbtTagCompound) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer");
            Object entityPlayer = craftPlayerClass.getMethod("getHandle").invoke(player);
            Class<?> nbtClass = Class.forName("net.minecraft.server.v1_16_R3.NBTTagCompound");

            entityPlayer.getClass().getMethod("load", nbtClass).invoke(entityPlayer, nbtTagCompound);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load player data", exception);
        }
    }
}