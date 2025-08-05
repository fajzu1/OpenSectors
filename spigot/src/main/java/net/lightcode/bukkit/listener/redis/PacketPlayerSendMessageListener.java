package net.lightcode.bukkit.listener.redis;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.bukkit.helper.ChatHelper;
import net.lightcode.packet.impl.PlayerSendMessagePacket;
import net.lightcode.redis.PacketListener;
import org.bukkit.Bukkit;

public class PacketPlayerSendMessageListener extends PacketListener<PlayerSendMessagePacket> {

    private final BukkitSectorPlugin plugin;

    public PacketPlayerSendMessageListener(BukkitSectorPlugin plugin) {
        super(PlayerSendMessagePacket.class);

        this.plugin = plugin;
    }

    @Override
    public void handle(PlayerSendMessagePacket packet) {
        this.plugin.getServer().broadcastMessage(ChatHelper.colored("&7" + packet.name() + ": " + packet.message()));
    }
}
