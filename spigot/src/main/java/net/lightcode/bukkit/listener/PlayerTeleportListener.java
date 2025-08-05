package net.lightcode.bukkit.listener;

import net.lightcode.configuration.impl.MessagesConfiguration;
import net.lightcode.bukkit.helper.ChatHelper;
import net.lightcode.bukkit.region.service.BukkitSectorRegionService;
import net.lightcode.sector.service.SectorService;
import net.lightcode.sector.type.SectorType;
import net.lightcode.bukkit.transfer.PlayerTransferService;
import net.lightcode.bukkit.user.service.UserService;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    private final UserService userService;

    private final SectorService sectorService;

    private final BukkitSectorRegionService bukkitSectorRegionService;

    private final PlayerTransferService transferService;

    private final MessagesConfiguration messagesConfiguration;

    public PlayerTeleportListener(SectorService sectorService, UserService userService, BukkitSectorRegionService bukkitSectorRegionService, PlayerTransferService transferService, MessagesConfiguration messagesConfiguration) {
        this.sectorService = sectorService;
        this.userService = userService;
        this.bukkitSectorRegionService = bukkitSectorRegionService;
        this.transferService = transferService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled()) return;

        if (!event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) return;

        final Player player = event.getPlayer();
        final Location locationTo = event.getTo();

        this.userService.find(player.getUniqueId()).ifPresent(user -> this.bukkitSectorRegionService.find(locationTo).ifPresent(sector -> {
            if (sector.sectorType().equals(SectorType.SPAWN)) {
                sector = this.sectorService.findAvailableSpawnSector().orElse(sector);
            }

            if (!sector.isOnline()) {
                this.bukkitSectorRegionService.knock(player);

                player.sendMessage(ChatHelper.colored(this.messagesConfiguration.sectorIsOfflineMessage()));
                return;
            }

            user.setRedirecting(true);

            this.transferService.connect(player, user, sector);
        }));
    }
}