package net.lightcode.bukkit.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreboardProfileFacade {

    String title(Player player);

    List<String> lines(Player player);
}
