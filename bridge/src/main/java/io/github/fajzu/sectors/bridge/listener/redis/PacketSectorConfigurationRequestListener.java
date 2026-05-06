package io.github.fajzu.sectors.bridge.listener.redis;

import io.github.fajzu.sectors.bridge.BridgePlugin;
import io.github.fajzu.shared.network.packet.PacketHandler;
import io.github.fajzu.shared.network.packet.internal.SectorConfigurationRequestPacket;
import io.github.fajzu.shared.network.packet.internal.SectorConfigurationResponsePacket;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorService;
import org.slf4j.Logger;

import com.google.inject.Inject;

public class PacketSectorConfigurationRequestListener {

    private final SectorService sectorService;
    private final BridgePlugin plugin;
    private final Logger logger;

    @Inject
    public PacketSectorConfigurationRequestListener(final SectorService sectorService,
                                                    final BridgePlugin plugin,
                                                    final Logger logger) {
        this.sectorService = sectorService;
        this.plugin = plugin;
        this.logger = logger;
    }

    @PacketHandler
    public void handle(final SectorConfigurationRequestPacket packet) {
        this.logger.info("Received SectorConfigurationRequestPacket from sender: {}", packet.sender());

        if (this.sectorService.find(packet.sender()) == null) {
            this.plugin.networkService().publish(
                    packet.sender(),
                    new SectorConfigurationResponsePacket(null)
            );

            this.logger.warn("No sector found for sender: {}", packet.sender());
            return;
        }

        this.logger.info("Sector found for sender: {}. Sending sector configuration response.", packet.sender());

        this.plugin.networkService().publish(
                packet.sender(),
                new SectorConfigurationResponsePacket(this.sectorService.sectors().values().toArray(new Sector[0]))
        );

        this.logger.warn("SectorConfigurationResponsePacket sent to sender: {}", packet.sender());
    }
}
