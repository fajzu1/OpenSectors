package io.github.fajzu.sectors.bukkit.packet;

import io.github.fajzu.sectors.bukkit.profile.Profile;
import io.github.fajzu.sectors.bukkit.profile.ProfileCache;
import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.network.packet.PacketHandler;
import io.github.fajzu.shared.network.packet.PacketTopic;
import io.github.fajzu.shared.network.packet.internal.PlayerConnectSectorPacket;
import io.github.fajzu.shared.network.packet.internal.PlayerTransferRequestPacket;

import com.google.inject.Inject;
import io.github.fajzu.shared.sector.SectorService;
import org.jetbrains.annotations.NotNull;

@PacketTopic(value = "transfer-request")
public class PacketPlayerTransferRequestListener {

    private final ProfileCache profileService;
    private final NetworkService networkService;
    private final SectorService sectorService;

    @Inject
    public PacketPlayerTransferRequestListener(final @NotNull ProfileCache profileService,
                                               final @NotNull NetworkService networkService,
                                               final @NotNull SectorService sectorService) {
        this.profileService = profileService;
        this.networkService = networkService;
        this.sectorService = sectorService;
    }

    @PacketHandler
    void handle(final PlayerTransferRequestPacket packet) {
        final Profile profile = this.profileService.find(packet.name());

        if (profile == null) {
            return;
        }

        this.networkService.publish("bridge", new PlayerConnectSectorPacket(profile.name(), this.sectorService.currentSectorId()));
    }
}
