package net.lightcode.bukkit.listener.redis;

import net.lightcode.bukkit.region.BukkitSectorRegion;
import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.packet.impl.SectorConfigurationResponsePacket;
import net.lightcode.redis.PacketListener;
import net.lightcode.sector.Sector;

import java.util.List;

public class PacketSectorConfigurationResponseListener extends PacketListener<SectorConfigurationResponsePacket> {

    private final BukkitSectorPlugin plugin;

    public PacketSectorConfigurationResponseListener(BukkitSectorPlugin plugin) {
        super(SectorConfigurationResponsePacket.class);

        this.plugin = plugin;
    }

    @Override
    public void handle(SectorConfigurationResponsePacket packet) {
        this.plugin.logger().log("Received SectorConfigurationResponsePacket with " + List.of(packet.sectors()).size() + " sectors.");

        this.plugin.sectorService().load(packet.sectors());

        for (Sector sector : packet.sectors()) {
            this.plugin.bukkitSectorRegionService().create(sector.id(), new BukkitSectorRegion(sector.region()));
            this.plugin.logger().log("Created BukkitSectorRegion for sector ID: " + sector.id());
        }

        this.plugin.registerSector();

        this.plugin.logger().log("Sectors registered.");
        this.plugin.logger().log("SectorConfigurationResponsePacket handling completed.");
    }
}
