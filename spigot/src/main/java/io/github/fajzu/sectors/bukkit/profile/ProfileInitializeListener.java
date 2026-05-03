package io.github.fajzu.sectors.bukkit.profile;

import com.google.common.base.Stopwatch;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.sector.SectorInitialize;
import io.github.fajzu.shared.sector.SectorService;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.inject.Inject;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.TimeUnit;

@SectorInitialize
public class ProfileInitializeListener implements Listener {

    private final BukkitSectorPluginController plugin;
    private final ProfileService profileService;
    private final MessagesConfiguration messagesConfiguration;
    private final SectorService sectorService;

    @Inject
    public ProfileInitializeListener(final BukkitSectorPluginController plugin,
                                     final ProfileService profileService,
                                     final MessagesConfiguration messagesConfiguration,
                                     final SectorService sectorService) {
        this.plugin = plugin;
        this.profileService = profileService;
        this.messagesConfiguration = messagesConfiguration;
        this.sectorService = sectorService;
    }

    @EventHandler
    void onPlayerLogin(final PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        if (this.sectorService.sectors().isEmpty()) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatHelper.colored(this.messagesConfiguration.noSectorsAvailableMessage()));
            return;
        }

        final Profile profile = this.profileService.find(player.getUniqueId());

        if (profile != null) {
            return;
        }

        this.profileService.create(player.getUniqueId(), player.getName());
    }

    @EventHandler
    void onPlayerJoin(final PlayerJoinEvent event) {
        event.joinMessage(Component.empty());

        final Stopwatch stopwatch = Stopwatch.createStarted();
        final Player player = event.getPlayer();
        final Profile profile = this.profileService.find(player.getUniqueId());

        if (profile == null) {
            player.kick(ChatHelper.colored(this.messagesConfiguration.playerDataNotFoundMessage()));
            return;
        }

        if (profile.isRedirecting()) {
            profile.loadData(player, this.plugin);

            profile.setRedirecting(false);
            profile.setTransferCooldown(true);

            player.sendMessage(ChatHelper.colored(this.messagesConfiguration.playerDataLoadedMessage()).replace("{TIME}", String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS))));
        }

        player.sendTitle(ChatHelper.colored(this.messagesConfiguration.connectedInfoTitle()), ChatHelper.colored(this.messagesConfiguration.connectedInfoSubTitle()).replace("{SECTOR}", this.sectorService.currentSectorId()));
    }

    @EventHandler
    void onPlayerQuit(final PlayerQuitEvent event) {
        event.quitMessage(Component.empty());

        final Player player = event.getPlayer();

        this.profileService.remove(player.getUniqueId());
    }
}
