package net.lightcode.v1_11R1.actionbar;

import net.lightcode.actionbar.Actionbar;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class v1_11R1Actionbar implements Actionbar {
    @Override
    public void sendActionBar(Player player, String message) {
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer");
            Object entityPlayer = craftPlayerClass.getMethod("getHandle").invoke(player);

            Class<?> iChatBaseComponent = Class.forName("net.minecraft.server.v1_11_R1.IChatBaseComponent");
            Class<?> chatSerializer = Class.forName("net.minecraft.server.v1_11_R1.IChatBaseComponent$ChatSerializer");
            Object baseComponent = chatSerializer.getMethod("a", String.class).invoke(null, "{\"text\":\"" + message + "\"}");

            Class<?> packetPlayOutChat = Class.forName("net.minecraft.server.v1_11_R1.PacketPlayOutChat");
            Constructor<?> constructor = packetPlayOutChat.getConstructor(iChatBaseComponent, byte.class);
            Object packet = constructor.newInstance(baseComponent, (byte) 2);

            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Class<?> packetClass = Class.forName("net.minecraft.server.v1_11_R1.Packet");

            playerConnection.getClass().getMethod("sendPacket", packetClass).invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
