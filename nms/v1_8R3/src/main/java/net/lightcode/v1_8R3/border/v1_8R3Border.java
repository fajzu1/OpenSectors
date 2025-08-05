package net.lightcode.v1_8R3.border;

import net.lightcode.border.Border;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class v1_8R3Border implements Border {
    @Override
    public void sendWorldBorder(Player player, double size, double centerX, double centerZ) {
        try {
            Object worldBorder = Class.forName("net.minecraft.server.v1_8_R3.WorldBorder").newInstance();

            Method setCenter = worldBorder.getClass().getMethod("setCenter", double.class, double.class);
            setCenter.invoke(worldBorder, centerX, centerZ);

            Method setSize = worldBorder.getClass().getMethod("setSize", double.class);
            setSize.invoke(worldBorder, size);

            Class<?> enumBorderAction = Class.forName("net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder$EnumWorldBorderAction");
            Object initializeAction = Enum.valueOf((Class<Enum>) enumBorderAction, "INITIALIZE");

            Constructor<?> packetConstructor = Class.forName("net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder")
                    .getConstructor(Class.forName("net.minecraft.server.v1_8_R3.WorldBorder"), enumBorderAction);
            Object packet = packetConstructor.newInstance(worldBorder, initializeAction);

            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            Method sendPacket = playerConnection.getClass().getMethod("sendPacket", Class.forName("net.minecraft.server.v1_8_R3.Packet"));
            sendPacket.invoke(playerConnection, packet);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
