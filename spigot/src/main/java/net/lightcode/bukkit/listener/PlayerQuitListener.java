package net.lightcode.bukkit.listener;

import net.lightcode.bukkit.user.service.UserService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final UserService userService;

    public PlayerQuitListener(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        final Player player = event.getPlayer();

        this.userService.remove(player.getUniqueId());
    }
}
