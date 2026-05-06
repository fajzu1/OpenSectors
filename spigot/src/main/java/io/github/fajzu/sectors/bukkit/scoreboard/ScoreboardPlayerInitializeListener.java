package io.github.fajzu.sectors.bukkit.scoreboard;

import com.google.inject.Inject;
import io.github.fajzu.shared.sector.SectorInitialize;
import io.github.fajzu.shared.sector.SectorType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

@SectorInitialize(type = SectorType.SPAWN)
public class ScoreboardPlayerInitializeListener implements Listener {

    private final ScoreboardPlayerService scoreboardPlayerService;

    @Inject
    public ScoreboardPlayerInitializeListener(final @NotNull ScoreboardPlayerService scoreboardPlayerService) {
        this.scoreboardPlayerService = scoreboardPlayerService;
    }

    @EventHandler
    void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.scoreboardPlayerService.create(
            player.getUniqueId(),
            ScoreboardPlayerFactory.create(
                player,
                this.scoreboardPlayerService.profile())
        );
    }

    @EventHandler
    void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        this.scoreboardPlayerService.remove(player.getUniqueId());
    }
}
