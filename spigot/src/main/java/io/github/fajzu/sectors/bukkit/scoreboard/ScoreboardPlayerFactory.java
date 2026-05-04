package io.github.fajzu.sectors.bukkit.scoreboard;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class ScoreboardPlayerFactory {
    public static ScoreboardPlayer create(
        final @NotNull Player player,
        final @NotNull ScoreboardProfileFacade profile) {
        return new ScoreboardPlayer(
            player,
            profile
        );
    }
}
