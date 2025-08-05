package net.lightcode.v1_15R1.data;

import net.lightcode.data.Data;
import org.bukkit.entity.Player;

public class v1_15R1Data implements Data<Player, Object> {
    @Override
    public String saveData(Player player) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer");
            Object entityPlayer = craftPlayerClass.getMethod("getHandle").invoke(player);

            Class<?> nbtClass = Class.forName("net.minecraft.server.v1_15_R1.NBTTagCompound");
            Object nbtTag = nbtClass.getConstructor().newInstance();

            entityPlayer.getClass().getMethod("b", nbtClass).invoke(entityPlayer, nbtTag);
            return nbtTag.toString();
        } catch (Exception exception) {
            throw new RuntimeException("Failed to save player data", exception);
        }
    }

    @Override
    public void loadData(Player player, Object nbtTagCompound) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer");
            Object entityPlayer = craftPlayerClass.getMethod("getHandle").invoke(player);

            Class<?> nbtClass = Class.forName("net.minecraft.server.v1_15_R1.NBTTagCompound");

            entityPlayer.getClass().getMethod("a", nbtClass).invoke(entityPlayer, nbtTagCompound);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load player data", exception);
        }
    }
}
