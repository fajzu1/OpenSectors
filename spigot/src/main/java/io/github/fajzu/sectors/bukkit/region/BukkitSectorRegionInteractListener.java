package io.github.fajzu.sectors.bukkit.region;

import io.github.fajzu.sectors.bukkit.BukkitSectorPluginController;
import io.github.fajzu.sectors.bukkit.helper.ChatHelper;
import io.github.fajzu.sectors.bukkit.profile.Profile;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.google.inject.Inject;

public class BukkitSectorRegionInteractListener implements Listener {

    private final BukkitSectorPluginController plugin;

    @Inject
    public BukkitSectorRegionInteractListener(final BukkitSectorPluginController plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onBlockBreak(final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Location location = event.getBlock().getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);

            player.sendMessage(ChatHelper.colored(this.plugin.messagesConfiguration().cannotBreakBlockNearSectorMessage()));
            return;
        }

        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onBlockPlace(final BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final Location location = event.getBlock().getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);

            player.sendMessage(ChatHelper.colored(this.plugin.messagesConfiguration().cannotPlaceBlockNearSectorMessage()));
            return;
        }

        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onBucketFill(final PlayerBucketFillEvent event) {
        final Player player = event.getPlayer();

        if (event.getBlockClicked() == null) {
            return;
        }

        final Location location = event.getBlockClicked().getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);
            return;
        }

        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onBucketEmpty(final PlayerBucketEmptyEvent event) {
        final Player player = event.getPlayer();

        if (event.getBlockClicked() == null) {
            return;
        }

        final Location location = event.getBlockClicked().getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);
            return;
        }

        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onBlockExplode(final BlockExplodeEvent event) {
        final Location location = event.getBlock().getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onEntityExplode(final EntityExplodeEvent event) {
        if (this.plugin.bukkitSectorRegionService().distance(event.getLocation()) <= 30) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    void onDropItem(final PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        final Location location = player.getLocation();

        if (this.plugin.bukkitSectorRegionService().distance(location) <= 30) {
            event.setCancelled(true);
        }

        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onEntityDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        final Profile profile = this.plugin.profileService().find(event.getEntity().getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onProjectileLaunch(final ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        final Profile profile = this.plugin.profileService().find(event.getEntity().getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onPickupItem(final PlayerPickupItemEvent event) {
        final Player player = event.getPlayer();
        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onOpen(final InventoryOpenEvent event) {
        final Player player = (Player) event.getPlayer();
        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onDrag(final InventoryDragEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    void onInteract(final InventoryInteractEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Profile profile = this.plugin.profileService().find(player.getUniqueId());

        if (profile == null || !profile.isRedirecting()) {
            return;
        }

        event.setCancelled(true);
    }
}
