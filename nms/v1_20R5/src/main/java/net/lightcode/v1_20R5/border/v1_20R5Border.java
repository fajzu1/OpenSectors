package net.lightcode.v1_20R5.border;

import net.lightcode.border.Border;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class v1_20R5Border implements Border {
    @Override
    public void sendWorldBorder(Player player, double size, double centerX, double centerZ) {
        WorldBorder worldBorder = Bukkit.createWorldBorder();

        worldBorder.setWarningDistance(0);

        worldBorder.setSize(size);

        worldBorder.setCenter(centerX, centerZ);

        player.setWorldBorder(worldBorder);
    }
}