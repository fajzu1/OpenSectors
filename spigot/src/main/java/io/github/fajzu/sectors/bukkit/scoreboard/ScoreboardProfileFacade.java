package io.github.fajzu.sectors.bukkit.scoreboard;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ScoreboardProfileFacade {

    String title(final @NotNull Player player);

    List<String> lines(final @NotNull Player player);
}
