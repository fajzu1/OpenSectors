package io.github.fajzu.sectors.bukkit.integration;

import io.github.fajzu.shared.network.NetworkService;
import io.github.fajzu.shared.network.packet.Packet;
import io.github.fajzu.shared.network.internal.RedisPacketListener;
import io.github.fajzu.shared.sector.Sector;
import io.github.fajzu.shared.sector.SectorService;
import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.profile.Profile;
import io.github.fajzu.sectors.bukkit.profile.ProfileCache;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BukkitSectorIntegration {

    private final BukkitSectorPluginController instance;

    private BukkitSectorIntegration() {
        this.instance = BukkitSectorPluginController.getPlugin(BukkitSectorPluginController.class);
    }

    public static BukkitSectorIntegration create() {
        return new BukkitSectorIntegration();
    }

    public Profile findProfileByUUID(final UUID uuid) {
        return this.instance.profileService().find(uuid);
    }

    public Profile findProfileByName(final String name) {
        return this.instance.profileService().find(name);
    }

    public Sector findSectorByName(final String name) {
        return this.instance.sectorService().find(name);
    }

    public Sector findSectorByLocation(final Location location) {
        return this.instance.bukkitSectorRegionService().find(location);
    }

    public Sector currentSector() {
        return this.instance.sectorService().currentSector();
    }

    public List<Sector> allSectors() {
        return new ArrayList<>(this.instance.sectorService().sectors().values());
    }

    public List<Profile> allProfiles() {
        return new ArrayList<>(this.instance.profileService().profiles().values());
    }

    public void sendPacket(final String channel,
                           final Packet packet) {
        this.instance.networkService().publish(channel, packet);
    }

    public void subscribe(final String channel,
                          final RedisPacketListener<?> listener) {
        this.instance.networkService().subscribe(channel, listener);
    }

    public SectorService sectorService() {
        return this.instance.sectorService();
    }

    public ProfileCache profileService() {
        return this.instance.profileService();
    }

    public NetworkService messengerService() {
        return this.instance.networkService();
    }

    public BukkitSectorPluginController instance() {
        return this.instance;
    }
}
