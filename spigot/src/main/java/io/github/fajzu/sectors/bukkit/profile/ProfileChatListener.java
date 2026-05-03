package io.github.fajzu.sectors.bukkit.profile;

import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.network.packet.internal.PlayerSendMessagePacket;
import io.github.fajzu.shared.sector.SectorInitialize;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;

@SectorInitialize
public class ProfileChatListener implements Listener {

    private final NetworkService networkService;

    @Inject
    public ProfileChatListener(final @NotNull NetworkService networkService) {
        this.networkService = networkService;
    }

    @EventHandler
    void onPlayerChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();

        event.setCancelled(true);

        this.networkService.publish("sectors", new PlayerSendMessagePacket(player.getName(), event.getMessage()));
    }
}
