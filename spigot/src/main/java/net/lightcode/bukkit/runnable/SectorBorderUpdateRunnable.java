package net.lightcode.bukkit.runnable;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.bukkit.region.BukkitSectorRegion;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SectorBorderUpdateRunnable extends BukkitRunnable {

    private final BukkitSectorPlugin plugin;

    public SectorBorderUpdateRunnable(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        final BukkitSectorRegion sector = this.plugin.bukkitSectorRegionService().currentSectorRegion();

        final double sectorSize = Math.abs(sector.maximumPoint().getX() - sector.minimumPoint().getX()) + 1.1;

        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            this.plugin.nmsService().border().sendWorldBorder(player, sectorSize, sector.center().getX(), sector.center().getZ());
        }
    }
}