package net.lightcode.v1_16R3.border;

import net.lightcode.border.Border;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class v1_16R3Border implements Border {
    @Override
    public void sendWorldBorder(Player player, double size, double centerX, double centerZ) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer");
            Object handle = craftPlayerClass.getMethod("getHandle").invoke(player);

            Class<?> worldBorderClass = Class.forName("net.minecraft.server.v1_16_R3.WorldBorder");
            Object worldBorder = worldBorderClass.getConstructor().newInstance();

            Field worldField = worldBorderClass.getDeclaredField("world");
            worldField.setAccessible(true);
            Object world = handle.getClass().getMethod("getWorld").invoke(handle);
            worldField.set(worldBorder, world);

            Method setCenter = worldBorderClass.getMethod("setCenter", double.class, double.class);
            setCenter.invoke(worldBorder, centerX, centerZ);

            Method setSize = worldBorderClass.getMethod("setSize", double.class);
            setSize.invoke(worldBorder, size);

            Class<?> actionClass = Class.forName("net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder$EnumWorldBorderAction");
            Object action = Enum.valueOf((Class<Enum>) actionClass, "INITIALIZE");

            Class<?> packetClass = Class.forName("net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder");
            Constructor<?> packetConstructor = packetClass.getConstructor(worldBorderClass, actionClass);
            Object packet = packetConstructor.newInstance(worldBorder, action);

            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            Method sendPacket = playerConnection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server.v1_16_R3.Packet"));
            sendPacket.invoke(playerConnection, packet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}