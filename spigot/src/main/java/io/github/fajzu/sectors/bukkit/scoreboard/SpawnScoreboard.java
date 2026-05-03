package io.github.fajzu.sectors.bukkit.scoreboard;

import io.github.fajzu.shared.configuration.internal.MessagesConfiguration;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnScoreboard implements ScoreboardProfileFacade {

    private final SectorService sectorService;
    private final MessagesConfiguration messagesConfiguration;

    public SpawnScoreboard(final SectorService sectorService,
                           final MessagesConfiguration messagesConfiguration) {
        this.sectorService = sectorService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @Override
    public String title(final @NonNull Player player) {
        return this.messagesConfiguration.scoreboardTitle();
    }

    @Override
    public List<String> lines(final @NonNull Player player) {
        final List<String> lines = new ArrayList<>();

        for (final String scoreboardLines : this.messagesConfiguration.scoreboardLines()) {
            lines.add(scoreboardLines
                    .replace("{SECTOR}", this.sectorService.currentSectorId())
                    .replace("{ONLINE}", String.valueOf(this.sectorService.currentSector().statistics().players()))
                    .replace("{TPS}", ChatHelper.formatTps(this.sectorService.currentSector().statistics().tps())));
        }

        return lines;
    }
}
