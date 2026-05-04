package io.github.fajzu.sectors.bukkit.region;

import com.google.inject.Inject;
import io.github.fajzu.nms.api.NmsService;
import io.github.fajzu.shared.Schedule;
import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.network.packet.internal.SectorInformationUpdatePacket;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

@Schedule(period = 20)
public class SectorInformationUpdateRunnable extends BukkitRunnable {

    private final NetworkService networkService;
    private final Plugin plugin;
    private final NmsService nmsService;

    @Inject
    public SectorInformationUpdateRunnable(final @NotNull NetworkService networkService,
                                           final @NotNull Plugin plugin,
                                           final @NotNull NmsService nmsService) {
        this.networkService = networkService;
        this.plugin = plugin;
        this.nmsService = nmsService;
    }

    @Override
    public void run() {
        this.networkService.publish("sectors", new SectorInformationUpdatePacket(this.plugin.getServer().getOnlinePlayers().size(), this.nmsService.minecraftServer().tps()));
    }
}
