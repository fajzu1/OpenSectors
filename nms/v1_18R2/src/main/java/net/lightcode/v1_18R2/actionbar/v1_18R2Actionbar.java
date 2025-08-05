package net.lightcode.v1_18R2.actionbar;

import net.kyori.adventure.text.Component;
import net.lightcode.actionbar.Actionbar;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class v1_18R2Actionbar implements Actionbar {
    @Override
    public void sendActionBar(Player player, String message) {
        player.sendActionBar(Component.text(message));
    }
}
