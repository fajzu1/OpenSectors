package net.lightcode.bukkit.listener;

import net.lightcode.bukkit.scoreboard.ScoreboardPlayer;
import net.lightcode.bukkit.scoreboard.ScoreboardPlayerService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerScoreboardListener implements Listener {

    private final ScoreboardPlayerService scoreboardPlayerService;

    public PlayerScoreboardListener(ScoreboardPlayerService scoreboardPlayerService) {
        this.scoreboardPlayerService = scoreboardPlayerService;
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.scoreboardPlayerService.create(player.getUniqueId(), new ScoreboardPlayer(player, this.scoreboardPlayerService.profile()));
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        this.scoreboardPlayerService.remove(player.getUniqueId());
    }
}
