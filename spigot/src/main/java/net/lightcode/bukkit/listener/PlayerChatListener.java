package net.lightcode.bukkit.listener;

import net.lightcode.NetworkService;
import net.lightcode.packet.impl.PlayerSendMessagePacket;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private final NetworkService networkService;

    public PlayerChatListener(NetworkService networkService) {
        this.networkService = networkService;
    }

    @EventHandler
    void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        event.setCancelled(true);

        this.networkService.publish("sectors", new PlayerSendMessagePacket(player.getName(),event.getMessage()));
    }
}
