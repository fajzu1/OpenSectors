package io.github.fajzu.sectors.bridge.listener;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import io.github.fajzu.sectors.bridge.BridgePlugin;
import net.kyori.adventure.text.Component;

public class PlayerServerConnectListener {

    private final BridgePlugin plugin;

    @Inject
    public PlayerServerConnectListener(final BridgePlugin plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    void onServerPreConnect(final ServerPreConnectEvent event) {
        // Redis logic removed. Defaulting to default server selection.
    }
}
