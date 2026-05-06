package io.github.fajzu.sectors.bukkit.packet;

import com.google.inject.Inject;
import io.github.fajzu.shared.network.packet.PacketHandler;
import io.github.fajzu.shared.network.packet.PacketTopic;
import io.github.fajzu.shared.network.packet.internal.SectorInformationUpdatePacket;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorService;
import org.jetbrains.annotations.NotNull;

@PacketTopic(value = "sector-information-update")
public class PacketSectorInformationUpdateListener {

    private final SectorService sectorService;

    @Inject
    public PacketSectorInformationUpdateListener(final @NotNull SectorService sectorService) {
        this.sectorService = sectorService;
    }

    @PacketHandler
    public void handle(final SectorInformationUpdatePacket packet) {
        final Sector sector = this.sectorService.find(packet.sender());

        if (sector == null) {
            return;
        }

        sector.statistics().lastUpdate(System.currentTimeMillis());
        sector.statistics().players(packet.players());
        sector.statistics().tps(packet.tps());
    }
}
