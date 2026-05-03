package io.github.fajzu.sectors.bukkit.profile;

import io.github.fajzu.sectors.bukkit.region.BukkitSectorRegionService;
import io.github.fajzu.sectors.bukkit.transfer.PlayerTransferService;
import io.github.fajzu.shared.configuration.ConfigurationService;
import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorInitialize;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.shared.sector.SectorType;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

@SectorInitialize
public class ProfileRespawnListener implements Listener {

    private final SectorService sectorService;
    private final ProfileService profileService;
    private final BukkitSectorRegionService regionService;
    private final ConfigurationService configurationService;
    private final PlayerTransferService transferService;

    @Inject
    public ProfileRespawnListener(final @NotNull SectorService sectorService,
                                  final @NotNull ProfileService profileService,
                                  final @NotNull BukkitSectorRegionService regionService,
                                  final @NotNull ConfigurationService configurationService,
                                  final @NotNull PlayerTransferService transferService) {
        this.sectorService = sectorService;
        this.profileService = profileService;
        this.regionService = regionService;
        this.configurationService = configurationService;
        this.transferService = transferService;
    }

    @EventHandler
    void onPlayerRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = this.profileService.find(player.getUniqueId());
        final Sector sector = this.sectorService.find(SectorType.SPAWN);

        final MessagesConfiguration messagesConfiguration = this.configurationService.find(MessagesConfiguration.class);
        if(messagesConfiguration == null) {
            return;
        }

        if (profile == null) {
            player.kick(ChatHelper.colored(messagesConfiguration.playerDataNotFoundMessage()));
            return;
        }

        if (sector == null) {
            player.kick(ChatHelper.colored(messagesConfiguration.spawnSectorNotFoundMessage()));
            return;
        }

        event.setRespawnLocation(this.regionService.randomLocation(sector));
        this.transferService.connect(player, profile, sector, false);
    }
}
