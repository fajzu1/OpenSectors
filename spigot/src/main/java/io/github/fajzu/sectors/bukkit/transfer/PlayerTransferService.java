package io.github.fajzu.sectors.bukkit.transfer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.fajzu.sectors.bukkit.profile.ProfileService;
import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.network.packet.internal.PlayerTransferRequestPacket;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.shared.sector.SectorType;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.event.PlayerSectorChangeEvent;
import io.github.fajzu.sectors.bukkit.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@Singleton
public class PlayerTransferService {

    private final Plugin plugin;
    private final SectorService sectorService;
    private final ProfileService profileService;
    private final NetworkService networkService;

    @Inject
    public PlayerTransferService(final @NotNull Plugin plugin,
                                 final @NotNull SectorService sectorService,
                                 final @NotNull ProfileService profileService,
                                 final @NotNull NetworkService networkService) {
        this.plugin = plugin;
        this.sectorService = sectorService;
        this.profileService = profileService;
        this.networkService = networkService;
    }

    public void connect(final Player player,
                        final Profile profile,
                        final Sector sector,
                        final boolean transferCheck) {
        if (sector.sectorType() == SectorType.SPAWN
            && this.sectorService.currentSector().sectorType() == SectorType.SPAWN
            && !transferCheck) {
            return;
        }

        final PlayerSectorChangeEvent sectorChangeEvent = new PlayerSectorChangeEvent(player, this.sectorService.currentSector(), sector);
        this.plugin.getServer().getPluginManager().callEvent(sectorChangeEvent);

        if (sectorChangeEvent.isCancelled()) {
            return;
        }

        if (player.isInsideVehicle()) {
            player.leaveVehicle();
        }

        profile.saveData(player, this.plugin);

        CompletableFuture.runAsync(() -> this.profileService.profileRepository().update(profile)).thenAccept(unused -> {
            this.networkService.publish(sector.id(), new PlayerTransferRequestPacket(player.getName()));

            this.plugin.getLogger().info("Connection process finished for player " + player.getName());
        });
    }
}
