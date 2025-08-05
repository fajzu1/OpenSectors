package net.lightcode.bukkit.scoreboard;

import net.lightcode.bukkit.helper.ChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScoreboardPlayer {

    private final Player player;

    private final Scoreboard scoreboard;

    private final ScoreboardProfileFacade profile;

    public ScoreboardPlayer(Player player, ScoreboardProfileFacade profile) {
        this.player = player;
        this.profile = profile;

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        player.setScoreboard(this.scoreboard);
    }

    public static String lastFormattingCodesOf(String text) {
        StringBuilder codes = new StringBuilder();
        for (int i = text.length() - 1; i >= 0; i--) {
            if (text.charAt(i) == '§' && i < text.length() - 1) {
                codes.insert(0, text.substring(i, i + 2));
                i--;
            } else if (text.charAt(i) != '§' && codes.length() > 0) {
                break;
            }
        }
        return codes.toString();
    }

    public void update() {
        if (this.profile == null) return;

        Objective objective = Optional.ofNullable(this.scoreboard.getObjective("scoreSystem"))
                .orElseGet(() -> {
                    Objective obj = this.scoreboard.registerNewObjective("scoreSystem", "dummy");
                    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                    return obj;
                });

        objective.setDisplayName(ChatHelper.colored(this.profile.title(this.player)));

        List<String> lines = this.profile.lines(this.player)
                .stream()
                .limit(15)
                .collect(Collectors.toList());
        int size = lines.size();

        IntStream.range(0, size).forEach(i -> {
            String line = ChatHelper.colored(lines.get(size - i - 1));
            String entry = ChatColor.values()[i].toString();

            String teamName = "t" + i;

            Team team = this.scoreboard.getTeam(teamName);
            if (team == null) {
                team = this.scoreboard.registerNewTeam(teamName);
                team.addEntry(entry);
            }

            String prefix = line.length() <= 16 ? line : line.substring(0, 16);
            String suffix = line.length() <= 16 ? "" : line.substring(16, Math.min(line.length(), 64));

            String lastCodes = lastFormattingCodesOf(prefix);

            if (!suffix.isEmpty()) {
                suffix = lastCodes + suffix;
            }

            if (!prefix.equals(team.getPrefix())) team.setPrefix(prefix);
            if (!suffix.equals(team.getSuffix())) team.setSuffix(suffix);

            objective.getScore(entry).setScore(i + 1);
        });

        List<String> usedEntries = IntStream.range(0, size)
                .mapToObj(i -> ChatColor.values()[i].toString())
                .collect(Collectors.toList());

        this.scoreboard.getEntries().stream()
                .filter(entry -> !usedEntries.contains(entry))
                .forEach(entry -> {
                    this.scoreboard.resetScores(entry);
                    Optional.ofNullable(this.scoreboard.getTeam("t" + this.findIndex(entry)))
                            .ifPresent(Team::unregister);
                });
    }

    private int findIndex(String entry) {
        return IntStream.range(0, ChatColor.values().length)
                .filter(i -> ChatColor.values()[i].toString().equals(entry))
                .findFirst()
                .orElse(-1);
    }

    public void remove() {
        this.scoreboard.getTeams().forEach(Team::unregister);
        this.scoreboard.getObjectives().forEach(Objective::unregister);
    }

    public Player player() {
        return this.player;
    }
}