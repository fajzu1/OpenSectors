package net.lightcode.bukkit.runnable;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.bukkit.helper.ChatHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class ActionBarNotificationRunnable extends BukkitRunnable {

    private final BukkitSectorPlugin plugin;

    public ActionBarNotificationRunnable(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {

            final Location location = player.getLocation();

            double distance = this.plugin.bukkitSectorRegionService().distance(location);

            if (distance > 30.0) continue;

            this.plugin.nmsService().actionBar().sendActionBar(player,
                    ChatHelper.colored(this.plugin.messagesConfiguration().actionbarBorderMessage())
                            .replace("{DISTANCE}", String.format("%.2f", distance)));
        }
    }
}
