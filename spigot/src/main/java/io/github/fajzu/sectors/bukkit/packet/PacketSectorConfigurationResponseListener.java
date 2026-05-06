package io.github.fajzu.sectors.bukkit.packet;

import com.google.inject.Inject;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.region.BukkitSectorRegion;
import io.github.fajzu.sectors.bukkit.region.BukkitSectorRegionCache;
import io.github.fajzu.sectors.bukkit.region.BukkitSectorRegionFactory;
import io.github.fajzu.sectors.bukkit.region.BukkitSectorRegionService;
import io.github.fajzu.shared.network.packet.PacketHandler;
import io.github.fajzu.shared.network.packet.PacketTopic;
import io.github.fajzu.shared.network.packet.internal.SectorConfigurationResponsePacket;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorService;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@PacketTopic(value = "sector-configuration-request")
public class PacketSectorConfigurationResponseListener {

    private final Plugin plugin;
    private final SectorService sectorService;
    private final BukkitSectorRegionCache regionCache;

    @Inject
    public PacketSectorConfigurationResponseListener(final @NotNull Plugin plugin,
                                                     final @NotNull SectorService sectorService,
                                                     final @NotNull BukkitSectorRegionCache regionCache) {
        this.plugin = plugin;
        this.sectorService = sectorService;
        this.regionCache = regionCache;
    }

    @PacketHandler
    void handle(final SectorConfigurationResponsePacket packet) {
        if (packet.sectors() == null) {
            this.plugin.getServer().shutdown();
            return;
        }

        for (final Sector sector : packet.sectors()) {
            this.sectorService.create(sector.id(), sector);
            this.regionCache.create(sector.id(), BukkitSectorRegionFactory.create(sector.region()));
        }
    }
}
