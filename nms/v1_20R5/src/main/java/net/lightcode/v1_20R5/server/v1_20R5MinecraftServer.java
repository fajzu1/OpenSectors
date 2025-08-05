package net.lightcode.v1_20R5.server;

import net.lightcode.server.MinecraftServer;

public class v1_20R5MinecraftServer implements MinecraftServer {
    @Override
    public double tps() {
        return net.minecraft.server.MinecraftServer.getServer().tps1.getAverage();
    }
}