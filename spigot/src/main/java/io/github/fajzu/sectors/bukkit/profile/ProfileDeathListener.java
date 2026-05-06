package io.github.fajzu.sectors.bukkit.profile;

import io.github.fajzu.shared.sector.SectorInitialize;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.google.inject.Inject;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@SectorInitialize
public class ProfileDeathListener implements Listener {

    private final Plugin plugin;

    @Inject
    public ProfileDeathListener(final @NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onPlayerDeath(final PlayerDeathEvent event) {
        event.deathMessage(Component.empty());

        final Player victim = event.getEntity();

        this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> victim.spigot().respawn(), 2L);
    }
}
