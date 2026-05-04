package io.github.fajzu.sectors.bukkit.region;

import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorInitialize;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.shared.sector.SectorType;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.sectors.bukkit.profile.Profile;
import io.github.fajzu.sectors.bukkit.profile.ProfileCache;
import io.github.fajzu.sectors.bukkit.transfer.PlayerTransferService;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.google.inject.Inject;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

@SectorInitialize
public final class BukkitSectorRegionMoveListener implements Listener {

    private final ProfileCache profileService;
    private final SectorService sectorService;
    private final BukkitSectorRegionService bukkitSectorRegionService;
    private final PlayerTransferService transferService;
    private final MessagesConfiguration messagesConfiguration;

    @Inject
    public BukkitSectorRegionMoveListener(final @NotNull SectorService sectorService,
                                          final @NotNull ProfileCache profileService,
                                          final @NotNull BukkitSectorRegionService bukkitSectorRegionService,
                                          final @NotNull PlayerTransferService transferService,
                                          final @NotNull MessagesConfiguration messagesConfiguration) {
        this.sectorService = sectorService;
        this.profileService = profileService;
        this.bukkitSectorRegionService = bukkitSectorRegionService;
        this.transferService = transferService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @EventHandler
    void onPlayerMove(final PlayerMoveEvent event) {
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

        if (profile.isTransferCooldown()) {
            this.bukkitSectorRegionService.knock(player);

            player.sendMessage(ChatHelper.colored(this.messagesConfiguration.playerSectorTransferCooldownMessage()));
            return;
        }

        if (profile.isRedirecting()) {
            return;
        }

        profile.redirecting(true);

        this.transferService.connect(player, profile, sector, false);
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

        profile.redirecting(true);

        this.transferService.connect(player, profile, sector, false);
    }
}
