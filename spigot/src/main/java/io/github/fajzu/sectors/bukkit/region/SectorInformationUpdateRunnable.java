package io.github.fajzu.sectors.bukkit.region;

import io.github.fajzu.shared.network.packet.internal.SectorInformationUpdatePacket;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import org.bukkit.scheduler.BukkitRunnable;

public class SectorInformationUpdateRunnable extends BukkitRunnable {

    private final BukkitSectorPluginController plugin;

    public SectorInformationUpdateRunnable(final BukkitSectorPluginController plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.plugin.networkService().publish("sectors", new SectorInformationUpdatePacket(this.plugin.getServer().getOnlinePlayers().size(), this.plugin.nmsService().minecraftServer().tps()));
    }
}
