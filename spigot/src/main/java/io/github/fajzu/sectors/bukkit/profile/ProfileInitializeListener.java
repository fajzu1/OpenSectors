package io.github.fajzu.sectors.bukkit.profile;

import com.google.common.base.Stopwatch;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.sector.SectorInitialize;
import io.github.fajzu.shared.sector.SectorService;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.inject.Inject;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@SectorInitialize
public class ProfileInitializeListener implements Listener {

    private final ProfileCache profileService;
    private final MessagesConfiguration messagesConfiguration;
    private final SectorService sectorService;
    private final ProfileRepository profileRepository;

    @Inject
    public ProfileInitializeListener(final @NotNull ProfileCache profileService,
                                     final @NotNull MessagesConfiguration messagesConfiguration,
                                     final @NotNull SectorService sectorService,
                                     final @NotNull ProfileRepository profileRepository) {
        this.profileService = profileService;
        this.messagesConfiguration = messagesConfiguration;
        this.sectorService = sectorService;
        this.profileRepository = profileRepository;
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

        final Player player = event.getPlayer();
        final Profile profile = this.profileService.find(player.getUniqueId());

        if (profile == null) {
            player.kick(ChatHelper.colored(this.messagesConfiguration.playerDataNotFoundMessage()));
            return;
        }

        if (profile.isRedirecting()) {
            this.profileRepository.loadData(player, profile);

            profile.redirecting(false);
            profile.transferCooldown(true);

            player.sendMessage(ChatHelper.colored(this.messagesConfiguration.playerDataLoadedMessage()));
        }

        player.showTitle(Title.title(
            ChatHelper.colored(this.messagesConfiguration.connectedInfoTitle()),
            ChatHelper.colored(this.messagesConfiguration.connectedInfoSubTitle())
        ));
    }

    @EventHandler
    void onPlayerQuit(final PlayerQuitEvent event) {
        event.quitMessage(Component.empty());

        final Player player = event.getPlayer();

        this.profileService.remove(player.getUniqueId());
    }
}
