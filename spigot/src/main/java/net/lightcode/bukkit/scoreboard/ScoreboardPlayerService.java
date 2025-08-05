package net.lightcode.bukkit.scoreboard;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardPlayerService {

    private final Map<UUID, ScoreboardPlayer> scoreboardPlayers = new ConcurrentHashMap<>();

    private final ScoreboardProfileFacade profile;

    public ScoreboardPlayerService(ScoreboardProfileFacade profile) {
        this.profile = profile;
    }

    public void create(UUID uuid, ScoreboardPlayer scoreboardPlayer) {
        this.scoreboardPlayers.put(uuid, scoreboardPlayer);
    }

    public Optional<ScoreboardPlayer> find(UUID uuid) {
        return Optional.ofNullable(this.scoreboardPlayers.get(uuid));
    }

    public void remove(UUID uuid) {
        this.scoreboardPlayers.remove(uuid);
    }

    public Map<UUID, ScoreboardPlayer> scoreboardPlayers() {
        return this.scoreboardPlayers;
    }

    public ScoreboardProfileFacade profile() {
        return this.profile;
    }
}
