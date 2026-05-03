package io.github.fajzu.sectors.bukkit.region;

import com.google.inject.Inject;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarNotificationRunnable extends BukkitRunnable {

    private final Plugin plugin;

    @Inject
    public ActionBarNotificationRunnable(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            final Location location = player.getLocation();
            final double distance = this.plugin.bukkitSectorRegionService().distance(location);

            if (distance > 30.0) {
                continue;
            }

            this.plugin.nmsService().actionBar().sendActionBar(player,
                    ChatHelper.colored(this.plugin.messagesConfiguration().actionbarBorderMessage())
                            .replace("{DISTANCE}", String.format("%.2f", distance)));
        }
    }
}
