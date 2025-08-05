package net.lightcode.bukkit.transfer;

import net.lightcode.bukkit.BukkitSectorPlugin;
import net.lightcode.bukkit.user.User;
import net.lightcode.bukkit.event.PlayerSectorChangeEvent;
import net.lightcode.helper.GsonHelper;
import net.lightcode.packet.impl.UserSynchronizeDataPacket;
import net.lightcode.sector.Sector;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class PlayerTransferService {

    private final BukkitSectorPlugin plugin;

    public PlayerTransferService(BukkitSectorPlugin plugin) {
        this.plugin = plugin;
    }

    public void connect(final Player player, final User user, final Sector sector) {
        this.plugin.getLogger().info("Starting connection process for player " + player.getName() + " to sector " + sector.id());

        this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
            PlayerSectorChangeEvent sectorChangeEvent = new PlayerSectorChangeEvent(player, this.plugin.sectorService().currentSector(), sector);
            this.plugin.getServer().getPluginManager().callEvent(sectorChangeEvent);

            if (sectorChangeEvent.isCancelled()) {
                this.plugin.getLogger().info("Connection cancelled by event for player " + player.getName());
                return;
            }

            if (player.isInsideVehicle()) {
                player.leaveVehicle();
            }

            this.plugin.getLogger().info("Saving user data for player " + player.getName());
            CompletableFuture.runAsync(() -> {
                user.saveData(player,this.plugin);

                this.plugin.messengerService().publish(sector.id(), new UserSynchronizeDataPacket(GsonHelper.toJson(user), sector.id()));

                this.plugin.getLogger().info("Connection process finished for player " + player.getName());
            });
        });
    }
}

