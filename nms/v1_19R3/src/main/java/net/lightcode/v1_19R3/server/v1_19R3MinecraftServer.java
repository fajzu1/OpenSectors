package net.lightcode.v1_19R3.server;

import net.lightcode.server.MinecraftServer;
import org.bukkit.Bukkit;

public class v1_19R3MinecraftServer implements MinecraftServer {
    @Override
    public double tps() {
        return Bukkit.getTPS()[0];
    }
}