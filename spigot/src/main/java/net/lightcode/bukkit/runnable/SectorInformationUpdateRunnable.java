package net.lightcode.bukkit.runnable;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.packet.impl.SectorInformationUpdatePacket;
import org.bukkit.scheduler.BukkitRunnable;

public class SectorInformationUpdateRunnable extends BukkitRunnable {

    private final BukkitSectorPlugin plugin;

    public SectorInformationUpdateRunnable(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        this.plugin.messengerService().publish("sectors", new SectorInformationUpdatePacket(this.plugin.getServer().getOnlinePlayers().size(), this.plugin.nmsService().minecraftServer().tps()));
    }
}
