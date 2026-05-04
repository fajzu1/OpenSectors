package io.github.fajzu.sectors.bukkit.region;

import com.google.inject.Inject;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.shared.Schedule;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@Schedule()
public class SectorActionBarNotificationRunnable extends BukkitRunnable {

    private final Plugin plugin;
    private final BukkitSectorRegionService regionService;

    @Inject
    public SectorActionBarNotificationRunnable(final @NotNull Plugin plugin,
                                               final @NotNull BukkitSectorRegionService regionService) {
        this.plugin = plugin;
        this.regionService = regionService;
    }

    @Override
    public void run() {
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            final Location location = player.getLocation();
            final double distance = this.regionService.distance(location);

            if (distance > 30.0) {
                continue;
            }

            this.plugin.nmsService().actionBar().sendActionBar(player,
                    ChatHelper.colored(this.plugin.messagesConfiguration().actionbarBorderMessage())
                            .replace("{DISTANCE}", String.format("%.2f", distance)));
        }
    }
}
