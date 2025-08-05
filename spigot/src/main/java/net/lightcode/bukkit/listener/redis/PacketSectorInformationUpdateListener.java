package net.lightcode.bukkit.listener.redis;

import net.lightcode.packet.impl.SectorInformationUpdatePacket;
import net.lightcode.redis.PacketListener;
import net.lightcode.sector.service.SectorService;

public class PacketSectorInformationUpdateListener extends PacketListener<SectorInformationUpdatePacket> {

    private final SectorService sectorService;

    public PacketSectorInformationUpdateListener(SectorService sectorService) {
        super(SectorInformationUpdatePacket.class);

        this.sectorService = sectorService;
    }

    @Override
    public void handle(SectorInformationUpdatePacket packet) {
        this.sectorService.find(packet.sender()).ifPresent(sector -> {
            sector.lastUpdate(System.currentTimeMillis());
            sector.players(packet.players());
            sector.tps(packet.tps());
        });
    }
}
