package net.lightcode.bridge.listener;

import net.lightcode.bridge.BridgePlugin;
import net.lightcode.packet.impl.PlayerConnectSectorPacket;
import net.lightcode.redis.PacketListener;

public class PacketPlayerConnectSectorListener extends PacketListener<PlayerConnectSectorPacket> {

    private final BridgePlugin plugin;

    public PacketPlayerConnectSectorListener(BridgePlugin plugin) {
        super(PlayerConnectSectorPacket.class);

        this.plugin = plugin;
    }

    @Override
    public void handle(PlayerConnectSectorPacket packet) {
        this.plugin.server().getPlayer(packet.name()).ifPresentOrElse(player -> {
            this.plugin.logger().log("Found player: {}, trying to connect to sector: {}", player.getUsername(), packet.sectorName());

            this.plugin.server().getServer(packet.sectorName()).ifPresentOrElse(registeredServer -> {
                this.plugin.logger().log("Found registered server: {}, sending connection request...", registeredServer.getServerInfo().getName());

                player.createConnectionRequest(registeredServer).connect();

                this.plugin.logger().log("Connection request sent to player: {}", player.getUsername());
            }, () -> this.plugin.logger().warning("Registered server not found: {}", packet.sectorName()));
        }, () -> this.plugin.logger().warning("Player not found: {}", packet.name()));
    }
}
