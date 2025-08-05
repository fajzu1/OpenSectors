package net.lightcode.v1_8R3.server;

import net.lightcode.server.MinecraftServer;

import java.lang.reflect.Field;

public class v1_9R2MinecraftServer implements MinecraftServer {
    @Override
    public double tps() {
        try {
            Class<?> minecraftServerClass = Class.forName("net.minecraft.server.v1_9_R2.MinecraftServer");
            Object server = minecraftServerClass.getMethod("getServer").invoke(null);
            Field recentTpsField = minecraftServerClass.getField("recentTps");
            double[] recentTps = (double[]) recentTpsField.get(server);
            return recentTps[0];
        } catch (Exception exception) {
            throw new RuntimeException("Failed to get TPS", exception);
        }
    }
}
