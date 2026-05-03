package io.github.fajzu.sectors.bukkit.region;

import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.shared.sector.SectorType;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.sectors.bukkit.profile.Profile;
import io.github.fajzu.sectors.bukkit.profile.ProfileService;
import io.github.fajzu.sectors.bukkit.transfer.PlayerTransferService;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.google.inject.Inject;

public class BukkitSectorRegionTeleportListener implements Listener {

    private final ProfileService profileService;
    private final SectorService sectorService;
    private final BukkitSectorRegionService bukkitSectorRegionService;
    private final PlayerTransferService transferService;
    private final MessagesConfiguration messagesConfiguration;

    @Inject
    public BukkitSectorRegionTeleportListener(final SectorService sectorService,
                                              final ProfileService profileService,
                                              final BukkitSectorRegionService bukkitSectorRegionService,
                                              final PlayerTransferService transferService,
                                              final MessagesConfiguration messagesConfiguration) {
        this.sectorService = sectorService;
        this.profileService = profileService;
        this.bukkitSectorRegionService = bukkitSectorRegionService;
        this.transferService = transferService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @EventHandler
    void onPlayerTeleport(final PlayerTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }

        final Player player = event.getPlayer();
        final Location location = event.getTo();
        final Profile profile = this.profileService.find(player.getUniqueId());

        Sector sector = this.bukkitSectorRegionService.find(location);

        if (profile == null || sector == null) {
            return;
        }

        if (sector.sectorType() == SectorType.SPAWN && this.sectorService.find(SectorType.SPAWN) != null) {
            sector = this.sectorService.find(SectorType.SPAWN);
        }

        if (!sector.statistics().isOnline()) {
            this.bukkitSectorRegionService.knock(player);

            player.sendMessage(ChatHelper.colored(this.messagesConfiguration.sectorIsOfflineMessage()));
            return;
        }

        profile.setRedirecting(true);

        this.transferService.connect(player, profile, sector, false);
    }
}
