package io.github.fajzu.sectors.bukkit.scoreboard;

import com.google.inject.Inject;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.shared.Schedule;
import org.bukkit.scheduler.BukkitRunnable;

@Schedule(period = 20)
public class ScoreboardUpdateRunnable implements Runnable {

    private final ScoreboardPlayerService scoreboardPlayerService;

    @Inject
    public ScoreboardUpdateRunnable(final ScoreboardPlayerService scoreboardPlayerService) {
        this.scoreboardPlayerService = scoreboardPlayerService;
    }

    @Override
    public void run() {
        for (final ScoreboardPlayer scoreboardPlayer : this.scoreboardPlayerService.scoreboardPlayers().values()) {
            scoreboardPlayer.update();
        }
    }
}
