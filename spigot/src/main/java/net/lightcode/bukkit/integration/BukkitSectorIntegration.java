package net.lightcode.bukkit.integration;

import net.lightcode.NetworkService;
import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.packet.Packet;
import net.lightcode.sector.Sector;
import net.lightcode.bukkit.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BukkitSectorIntegration {

    private final BukkitSectorPlugin plugin;

    private BukkitSectorIntegration(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    public static BukkitSectorIntegration create(BukkitSectorPlugin plugin) {
        return new BukkitSectorIntegration(plugin);
    }

    public Optional<User> findUserByUUID(UUID uuid) {
        return this.plugin.userService().find(uuid);
    }

    public Optional<User> findUserByName(String name) {
        return this.plugin.userService().users().values().stream()
                .filter(user -> user.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public Optional<Sector> findSectorByName(String name) {
        return this.plugin.sectorService().find(name);
    }

    public Sector currentSector() {
        return this.plugin.sectorService().currentSector();
    }

    public List<Sector> allSectors() {
        return new ArrayList<>(this.plugin.sectorService().sectors().values());
    }

    public List<User> allUsers() {
        return new ArrayList<>(this.plugin.userService().users().values());
    }

    public void sendPacket(String channel, Packet packet) {
       this.plugin.messengerService().publish(channel, packet);
    }

    public NetworkService messengerService() {
        return this.plugin.messengerService();
    }

    public BukkitSectorPlugin getPlugin() {
        return this.plugin;
    }

}