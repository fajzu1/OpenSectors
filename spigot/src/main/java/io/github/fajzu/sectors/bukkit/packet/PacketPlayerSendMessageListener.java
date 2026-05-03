package io.github.fajzu.sectors.bukkit.packet;

import com.google.inject.Inject;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.shared.network.packet.PacketHandler;
import io.github.fajzu.shared.network.packet.internal.PlayerSendMessagePacket;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PacketPlayerSendMessageListener {

    private final Plugin plugin;

    @Inject
    public PacketPlayerSendMessageListener(final @NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @PacketHandler
    public void handle(final PlayerSendMessagePacket packet) {
        this.plugin.getServer().broadcast(ChatHelper.colored("&7" + packet.name() + ": " + packet.message()));
    }
}
