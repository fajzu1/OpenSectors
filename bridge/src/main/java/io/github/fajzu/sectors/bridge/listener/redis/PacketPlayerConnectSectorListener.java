package io.github.fajzu.sectors.bridge.listener.redis;

import com.google.inject.Inject;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.github.fajzu.shared.network.packet.PacketHandler;
import io.github.fajzu.shared.network.packet.internal.PlayerConnectSectorPacket;
import io.github.fajzu.sectors.bridge.BridgePlugin;
import org.slf4j.Logger;

import java.util.Optional;

public class PacketPlayerConnectSectorListener {

    private final BridgePlugin plugin;
    private final Logger logger;

    @Inject
    public PacketPlayerConnectSectorListener(final BridgePlugin plugin,
                                             final Logger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    @PacketHandler
    public void handle(final PlayerConnectSectorPacket packet) {
        final Optional<Player> playerOptional = this.plugin.server().getPlayer(packet.name());

        if (playerOptional.isEmpty()) {
            this.logger.warn("Player not found: {}", packet.name());
            return;
        }

        final Player player = playerOptional.get();
        this.logger.info("Found player: {}, trying to connect to sector: {}", player.getUsername(), packet.sectorName());

        final Optional<RegisteredServer> serverOptional = this.plugin.server().getServer(packet.sectorName());

        if (serverOptional.isEmpty()) {
            this.logger.warn("Registered server not found: {}", packet.sectorName());
            return;
        }

        final RegisteredServer registeredServer = serverOptional.get();
        this.logger.info("Found registered server: {}, sending connection request...", registeredServer.getServerInfo().getName());

        player.createConnectionRequest(registeredServer).connect();
        this.logger.info("Connection request sent to player: {}", player.getUsername());
    }
}
