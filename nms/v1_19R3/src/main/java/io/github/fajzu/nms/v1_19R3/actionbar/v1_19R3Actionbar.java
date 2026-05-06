package io.github.fajzu.nms.v1_19R3.actionbar;

import io.github.fajzu.nms.api.actionbar.Actionbar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class v1_19R3Actionbar implements Actionbar {
    @Override
    public void sendActionBar(Player player,
                              String message) {
        player.sendActionBar(Component.text(message));
    }
}
