package net.lightcode.bridge.listener;

import net.lightcode.NetworkService;
import net.lightcode.bridge.BridgeLogger;
import net.lightcode.packet.impl.SectorConfigurationRequestPacket;
import net.lightcode.packet.impl.SectorConfigurationResponsePacket;
import net.lightcode.redis.PacketListener;
import net.lightcode.sector.Sector;
import net.lightcode.sector.service.SectorService;

public class PacketSectorConfigurationRequestListener extends PacketListener<SectorConfigurationRequestPacket> {

    private final SectorService sectorService;

    private final NetworkService networkService;

    private final BridgeLogger logger;

    public PacketSectorConfigurationRequestListener(SectorService sectorService, NetworkService networkService, BridgeLogger logger) {
        super(SectorConfigurationRequestPacket.class);

        this.sectorService = sectorService;
        this.networkService = networkService;
        this.logger = logger;
    }

    @Override
    public void handle(SectorConfigurationRequestPacket packet) {
        this.logger.log("Received SectorConfigurationRequestPacket from sender: {}", packet.sender());

        if (this.sectorService.find(packet.sender()).isEmpty()) {
            this.logger.warning("No sector found for sender: {}", packet.sender());
            return;
        }

        this.logger.log("Sector found for sender: {}. Sending sector configuration response.", packet.sender());

        this.networkService.publish(
                packet.sender(),
                new SectorConfigurationResponsePacket(this.sectorService.sectors().values().toArray(new Sector[0]))
        );

        this.logger.warning("SectorConfigurationResponsePacket sent to sender: {}", packet.sender());
    }
}