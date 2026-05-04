package io.github.fajzu.sectors.bukkit.region;

import com.google.inject.Inject;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.sectors.bukkit.profile.Profile;
import io.github.fajzu.sectors.bukkit.profile.ProfileCache;
import io.github.fajzu.shared.sector.SectorInitialize;
import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@SectorInitialize
public final class BukkitSectorRegionInteractListener implements Listener {

    private final BukkitSectorRegionService regionService;
    private final ProfileCache profileCache;
    private final Plugin plugin;

    @Inject
    public BukkitSectorRegionInteractListener(final @NotNull BukkitSectorRegionService regionService,
                                              final @NotNull ProfileCache profileCache,
                                              final @NotNull Plugin plugin) {
        this.regionService = regionService;
        this.profileCache = profileCache;
        this.plugin = plugin;
    }

    @EventHandler
    void onBlockBreak(final BlockBreakEvent event) {
        final Player player = event.getPlayer();

        this.handleInteract(
            player,
            event,
            event.getBlock().getLocation(),
            "",
            true,
            true
        );
    }

    @EventHandler
    void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();

        this.handleInteract(
            player,
            event,
            event.getBlockPlaced().getLocation(),
            "Elo",
            true,
            true
        );
    }

    @EventHandler
    void onBucketFill(final PlayerBucketFillEvent event) {
        final Player player = event.getPlayer();

        this.handleInteract(
            player,
            event,
            event.getBlockClicked().getLocation(),
            true
        );
    }

    @EventHandler
    void onBucketEmpty(final PlayerBucketEmptyEvent event) {
        final Player player = event.getPlayer();

        this.handleInteract(
            player,
            event,
            event.getBlockClicked().getLocation(),
            true
        );
    }

    @EventHandler
    void onBlockExplode(final BlockExplodeEvent event) {
        final Location location = event.getBlock().getLocation();

        if (this.regionService.distance(location) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onEntityExplode(final EntityExplodeEvent event) {
        if (this.regionService.distance(event.getLocation()) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onDropItem(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = this.profileCache.find(player.getUniqueId());

        if(profile == null || profile.isRedirecting()) {
            event.setCancelled(true);
            return;
        }

        if(!this.regionService.isNearBorder(player.getLocation(), 10)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onEntityDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        final Player player = (Player) event.getEntity();

        this.handleInteract(
            player,
            event,
            player.getLocation(),
            false
        );
    }

    @EventHandler
    void onProjectileLaunch(final ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        final Player player = (Player) event.getEntity().getShooter();

        this.handleInteract(
            player,
            event,
            event.getEntity().getLocation(),
            false
        );
    }

    @EventHandler
    void onInventoryOpen(final InventoryOpenEvent event) {
        final Player player = (Player) event.getPlayer();

        this.handleInteract(
            player,
            event,
            player.getLocation(),
            false
        );
    }

    @EventHandler
    void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        this.handleInteract(
            player,
            event,
            player.getLocation(),
            false
        );
    }

    @EventHandler
    void onInventoryDrag(final InventoryDragEvent event) {
        final Player player = (Player) event.getWhoClicked();

        this.handleInteract(
            player,
            event,
            player.getLocation(),
            false
        );
    }

    @EventHandler
    void onInventoryInteract(final InventoryInteractEvent event) {
        final Player player = (Player) event.getWhoClicked();

        this.handleInteract(
            player,
            event,
            player.getLocation(),
            false
        );
    }

    @EventHandler
    void onPlayerArmorStandManipulate(final PlayerArmorStandManipulateEvent event) {
        final Player player = event.getPlayer();

        this.handleInteract(player, event, event.getRightClicked().getLocation(), null, false, false);
    }

    @EventHandler
    void onBlockDispense(final BlockDispenseEvent event) {
        if (this.regionService.distance(event.getBlock().getLocation()) <= 10) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onVehicleMove(final VehicleMoveEvent event) {
        final Vehicle vehicle = event.getVehicle();

        if (vehicle instanceof Horse) {
            Horse horse = (Horse) vehicle;
            if (this.regionService.isNearBorder(horse.getLocation(), 10)) {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, vehicle::remove);
                return;
            }
        }


        if (this.regionService.isNearBorder(vehicle.getLocation(), 10)) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, vehicle::remove);
        }
    }

    @EventHandler
    void onVehicleEnter(final VehicleEnterEvent event) {
        final Vehicle vehicle = event.getVehicle();

        if (vehicle instanceof Horse) {
            Horse horse = (Horse) vehicle;
            if (this.regionService.isNearBorder(horse.getLocation(), 10)) {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, vehicle::remove);
                return;
            }
        }

        if (this.regionService.isNearBorder(vehicle.getLocation(), 10)) {
            event.setCancelled(true);
        }
    }

    private void handleInteract(
        final @NotNull Player player,
        final @NotNull Cancellable event,
        final Location location,
        final boolean distanceCheck
    ) {
        handleInteract(
            player,
            event,
            location,
            null,
            distanceCheck,
            false
        );
    }

    private void handleInteract(
        final @NotNull Player player,
        final @NotNull Cancellable event,
        final @NotNull Location location,
        final String notice,
        final boolean distanceCheck,
        final boolean messageSend) {
        final Profile profile = this.profileCache.find(player.getUniqueId());

        if (profile == null || profile.isRedirecting()) {
            event.setCancelled(true);
            return;
        }

        if (player.hasPermission("server.border.terrain.edit")) {
            return;
        }

        if (distanceCheck && this.regionService.distance(location) <= 10) {
            event.setCancelled(true);

            if (!messageSend || notice == null) {
                return;
            }

            player.sendMessage(ChatHelper.colored(notice));
        }
    }
}