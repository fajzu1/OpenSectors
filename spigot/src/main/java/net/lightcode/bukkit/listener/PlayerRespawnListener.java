package net.lightcode.bukkit.listener;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.bukkit.helper.ChatHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    private final BukkitSectorPlugin plugin;

    public PlayerRespawnListener(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();

        this.plugin.userService().find(player.getUniqueId()).ifPresent(user -> this.plugin.sectorService().findAvailableSpawnSector().ifPresentOrElse(sector -> this.plugin.transferService().connect(player, user, sector), () -> player.kickPlayer(ChatHelper.colored(this.plugin.messagesConfiguration().spawnSectorNotFoundMessage()))));


    }
}
