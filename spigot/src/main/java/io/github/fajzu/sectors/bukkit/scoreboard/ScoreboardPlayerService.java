package io.github.fajzu.sectors.bukkit.scoreboard;

import com.google.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public final class ScoreboardPlayerService {

    private final Map<UUID, ScoreboardPlayer> scoreboardPlayers = new ConcurrentHashMap<>();
    private final ScoreboardProfileFacade profile;

    public ScoreboardPlayerService(final ScoreboardProfileFacade profile) {
        this.profile = profile;
    }

    public void create(final @NotNull UUID uuid,
                       final @NotNull ScoreboardPlayer scoreboardPlayer) {
        this.scoreboardPlayers.put(uuid, scoreboardPlayer);
    }

    public ScoreboardPlayer find(final @NotNull UUID uuid) {
        return this.scoreboardPlayers.get(uuid);
    }

    public void remove(final @NotNull UUID uuid) {
        this.scoreboardPlayers.remove(uuid);
    }

    public Map<UUID, ScoreboardPlayer> scoreboardPlayers() {
        return this.scoreboardPlayers;
    }

    public ScoreboardProfileFacade profile() {
        return this.profile;
    }
}
