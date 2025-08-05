package net.lightcode.bukkit.listener;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.bukkit.helper.ChatHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    private final BukkitSectorPlugin plugin;

    public PlayerLoginListener(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerLogin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        this.plugin.logger().log("Player " + player.getName() + " is trying to log in.");
        if (this.plugin.sectorService().sectors().isEmpty()) {
            this.plugin.logger().warning("No sectors available. Kicking player " + player.getName());

            event.setKickMessage(ChatHelper.colored(this.plugin.messagesConfiguration().noSectorsAvailableMessage()));
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }

        this.plugin.userService().find(player.getUniqueId()).ifPresentOrElse(user -> this.plugin.logger().log("User data found for player " + player.getName()), () -> this.plugin.userService().create(player.getUniqueId(),player.getName()));
    }
}
