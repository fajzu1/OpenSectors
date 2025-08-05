package net.lightcode.bukkit.listener;

import net.lightcode.bukkit.BukkitSectorPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final BukkitSectorPlugin plugin;

    public PlayerDeathListener(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);

        if (event.getEntity() == null) return;

        final Player victim = event.getEntity();

        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> victim.spigot().respawn(), 2L);
    }
}
