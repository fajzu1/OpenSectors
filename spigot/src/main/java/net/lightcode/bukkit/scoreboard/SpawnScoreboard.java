package net.lightcode.bukkit.scoreboard;

import net.lightcode.bukkit.helper.ChatHelper;
import net.lightcode.configuration.impl.MessagesConfiguration;
import net.lightcode.sector.service.SectorService;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SpawnScoreboard implements ScoreboardProfileFacade {

    private final SectorService sectorService;

    private final MessagesConfiguration messagesConfiguration;

    public SpawnScoreboard(SectorService sectorService, MessagesConfiguration messagesConfiguration) {
        this.sectorService = sectorService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @Override
    public String title(Player player) {
        return ChatHelper.colored(this.messagesConfiguration.scoreboardTitle());
    }

    @Override
    public List<String> lines(Player player) {
        List<String> lines = new ArrayList<>();

        for (String scoreboardLines : this.messagesConfiguration.scoreboardLines()) {
            lines.add(ChatHelper.colored(scoreboardLines
                    .replace("{SECTOR}", this.sectorService.currentSectorId())
                    .replace("{ONLINE}", String.valueOf(this.sectorService.currentSector().players()))
                    .replace("{TPS}", ChatHelper.formatTps(this.sectorService.currentSector().tps()))));
        }

        return lines;
    }

}
